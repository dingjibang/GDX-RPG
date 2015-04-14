/*******************************************************************************
 * Copyright 2012 bmanuel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.bitfire.postprocessing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.bitfire.postprocessing.utils.PingPongBuffer;
import com.bitfire.utils.ItemsManager;

/** Provides a way to capture the rendered scene to an off-screen buffer and to apply a chain of effects on it before rendering to
 * screen.
 *
 * Effects can be added or removed via {@link #addEffect(PostProcessorEffect)} and {@link #removeEffect(PostProcessorEffect)}.
 *
 * @author bmanuel */
public final class PostProcessor implements Disposable {
	/** Enable pipeline state queries: beware the pipeline can stall! */
	public static boolean EnableQueryStates = false;

	private static PipelineState pipelineState = null;
	private static Format fbFormat;
	private final PingPongBuffer composite;
	private TextureWrap compositeWrapU;
	private TextureWrap compositeWrapV;
	private final ItemsManager<PostProcessorEffect> effectsManager = new ItemsManager<PostProcessorEffect>();
	private static final Array<PingPongBuffer> buffers = new Array<PingPongBuffer>(5);
	private final Color clearColor = Color.CLEAR;
	private int clearBits = GL20.GL_COLOR_BUFFER_BIT;
	private float clearDepth = 1f;
	private static Rectangle viewport = new Rectangle();
	private static boolean hasViewport = false;

	private boolean enabled = true;
	private boolean capturing = false;
	private boolean hasCaptured = false;
	private boolean useDepth = false;

	private PostProcessorListener listener = null;

	// maintains a per-frame updated list of enabled effects
	private Array<PostProcessorEffect> enabledEffects = new Array<PostProcessorEffect>(5);

	/** Construct a new PostProcessor with FBO dimensions set to the size of the screen */
	public PostProcessor (boolean useDepth, boolean useAlphaChannel, boolean use32Bits) {
		this(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), useDepth, useAlphaChannel, use32Bits);
	}

	/** Construct a new PostProcessor with the given parameters, defaulting to <em>TextureWrap.ClampToEdge</em> as texture wrap mode */
	public PostProcessor (int fboWidth, int fboHeight, boolean useDepth, boolean useAlphaChannel, boolean use32Bits) {
		this(fboWidth, fboHeight, useDepth, useAlphaChannel, use32Bits, TextureWrap.ClampToEdge, TextureWrap.ClampToEdge);
	}

	/** Construct a new PostProcessor with the given parameters and viewport, defaulting to <em>TextureWrap.ClampToEdge</em> as
	 * texture wrap mode */
	public PostProcessor (Rectangle viewport, boolean useDepth, boolean useAlphaChannel, boolean use32Bits) {
		this((int)viewport.width, (int)viewport.height, useDepth, useAlphaChannel, use32Bits, TextureWrap.ClampToEdge,
			TextureWrap.ClampToEdge);
		setViewport(viewport);
	}

	/** Construct a new PostProcessor with the given parameters, viewport and the specified texture wrap mode */
	public PostProcessor (Rectangle viewport, boolean useDepth, boolean useAlphaChannel, boolean use32Bits, TextureWrap u,
		TextureWrap v) {
		this((int)viewport.width, (int)viewport.height, useDepth, useAlphaChannel, use32Bits, u, v);
		setViewport(viewport);
	}

	/** Construct a new PostProcessor with the given parameters and the specified texture wrap mode */
	public PostProcessor (int fboWidth, int fboHeight, boolean useDepth, boolean useAlphaChannel, boolean use32Bits,
		TextureWrap u, TextureWrap v) {
		if (use32Bits) {
			if (useAlphaChannel) {
				fbFormat = Format.RGBA8888;
			} else {
				fbFormat = Format.RGB888;
			}
		} else {
			if (useAlphaChannel) {
				fbFormat = Format.RGBA4444;
			} else {
				fbFormat = Format.RGB565;
			}
		}

		composite = newPingPongBuffer(fboWidth, fboHeight, fbFormat, useDepth);
		setBufferTextureWrap(u, v);

		pipelineState = new PipelineState();

		capturing = false;
		hasCaptured = false;
		enabled = true;
		this.useDepth = useDepth;
		if (useDepth) {
			clearBits |= GL20.GL_DEPTH_BUFFER_BIT;
		}

		setViewport(null);
	}

	/** Creates and returns a managed PingPongBuffer buffer, just create and forget. If rebind() is called on context loss, managed
	 * PingPongBuffers will be rebound for you.
	 *
	 * This is a drop-in replacement for the same-signature PingPongBuffer's constructor. */
	public static PingPongBuffer newPingPongBuffer (int width, int height, Format frameBufferFormat, boolean hasDepth) {
		PingPongBuffer buffer = new PingPongBuffer(width, height, frameBufferFormat, hasDepth);
		buffers.add(buffer);
		return buffer;
	}

	/** Provides a way to query the pipeline for the most used states */
	public static boolean isStateEnabled (int pname) {
		if (EnableQueryStates) {
			// Gdx.app.log( "PipelineState", "Querying blending" );
			return pipelineState.isEnabled(pname);
		}

		// Gdx.app.log( "PipelineState", "(not querying)" );
		return false;
	}

	/** Sets the viewport to be restored, if null is specified then the viewport will NOT be restored at all.
	 *
	 * The predefined effects will restore the viewport settings at the final blitting stage (render to screen) by invoking the
	 * restoreViewport static method. */
	public void setViewport (Rectangle viewport) {
		PostProcessor.hasViewport = (viewport != null);
		if (hasViewport) {
			PostProcessor.viewport.set(viewport);
		}
	}

	/** Frees owned resources. */
	@Override
	public void dispose () {
		effectsManager.dispose();

		// cleanup managed buffers, if any
		for (int i = 0; i < buffers.size; i++) {
			buffers.get(i).dispose();
		}

		buffers.clear();

		if (enabledEffects != null) {
			enabledEffects.clear();
		}

		pipelineState.dispose();
	}

	/** Whether or not the post-processor is enabled */
	public boolean isEnabled () {
		return enabled;
	}

	/** If called before capturing it will indicate if the next capture call will succeeds or not. */
	public boolean isReady () {
		boolean hasEffects = false;

		for (PostProcessorEffect e : effectsManager) {
			if (e.isEnabled()) {
				hasEffects = true;
				break;
			}
		}

		return (enabled && !capturing && hasEffects);
	}

	/** Sets whether or not the post-processor should be enabled */
	public void setEnabled (boolean enabled) {
		this.enabled = enabled;
	}

	/** Returns the number of the currently enabled effects */
	public int getEnabledEffectsCount () {
		return enabledEffects.size;
	}

	/** Sets the listener that will receive events triggered by the PostProcessor rendering pipeline. */
	public void setListener (PostProcessorListener listener) {
		this.listener = listener;
	}

	/** Adds the specified effect to the effect chain and transfer ownership to the PostProcessor, it will manage cleaning it up for
	 * you. The order of the inserted effects IS important, since effects will be applied in a FIFO fashion, the first added is the
	 * first being applied. */
	public void addEffect (PostProcessorEffect effect) {
		effectsManager.add(effect);
	}

	/** Removes the specified effect from the effect chain. */
	public void removeEffect (PostProcessorEffect effect) {
		effectsManager.remove(effect);
	}

	/** Returns the internal framebuffer format, computed from the parameters specified during construction. NOTE: the returned
	 * Format will be valid after construction and NOT early! */
	public static Format getFramebufferFormat () {
		return fbFormat;
	}

	/** Sets the color that will be used to clear the buffer. */
	public void setClearColor (Color color) {
		clearColor.set(color);
	}

	/** Sets the color that will be used to clear the buffer. */
	public void setClearColor (float r, float g, float b, float a) {
		clearColor.set(r, g, b, a);
	}

	/** Sets the clear bit for when glClear is invoked. */
	public void setClearBits (int bits) {
		clearBits = bits;
	}

	/** Sets the depth value with which to clear the depth buffer when needed. */
	public void setClearDepth (float depth) {
		clearDepth = depth;
	}

	public void setBufferTextureWrap (TextureWrap u, TextureWrap v) {
		compositeWrapU = u;
		compositeWrapV = v;

		composite.texture1.setWrap(compositeWrapU, compositeWrapV);
		composite.texture2.setWrap(compositeWrapU, compositeWrapV);
	}

	/** Starts capturing the scene, clears the buffer with the clear color specified by {@link #setClearColor(Color)} or
	 * {@link #setClearColor(float r, float g, float b, float a)}.
	 *
	 * @return true or false, whether or not capturing has been initiated. Capturing will fail in case there are no enabled effects
	 *         in the chain or this instance is not enabled or capturing is already started. */
	public boolean capture () {
		hasCaptured = false;

		if (enabled && !capturing) {
			if (buildEnabledEffectsList() == 0) {
				// no enabled effects
				// Gdx.app.log( "PostProcessor::capture()",
				// "No post-processor effects enabled" );
				return false;
			}

			capturing = true;
			composite.begin();
			composite.capture();

			if (useDepth) {
				Gdx.gl.glClearDepthf(clearDepth);
			}

			Gdx.gl.glClearColor(clearColor.r, clearColor.g, clearColor.b, clearColor.a);
			Gdx.gl.glClear(clearBits);
			return true;
		}

		return false;
	}

	/** Starts capturing the scene as {@link #capture()}, but <strong>without</strong> clearing the screen.
	 *
	 * @return true or false, whether or not capturing has been initiated. */
	public boolean captureNoClear () {
		hasCaptured = false;

		if (enabled && !capturing) {
			if (buildEnabledEffectsList() == 0) {
				// no enabled effects
				// Gdx.app.log( "PostProcessor::captureNoClear",
				// "No post-processor effects enabled" );
				return false;
			}

			capturing = true;
			composite.begin();
			composite.capture();
			return true;
		}

		return false;
	}

	/** Stops capturing the scene and returns the result, or null if nothing was captured. */
	public FrameBuffer captureEnd () {
		if (enabled && capturing) {
			capturing = false;
			hasCaptured = true;
			composite.end();
			return composite.getResultBuffer();
		}

		return null;
	}

	public PingPongBuffer getCombinedBuffer () {
		return composite;
	}

	/** After a capture/captureEnd action, returns the just captured buffer */
	public FrameBuffer captured () {
		if (enabled && hasCaptured) {
			return composite.getResultBuffer();
		}

		return null;
	}

	/** Regenerates and/or rebinds owned resources when needed, eg. when the OpenGL context is lost. */
	public void rebind () {
		composite.texture1.setWrap(compositeWrapU, compositeWrapV);
		composite.texture2.setWrap(compositeWrapU, compositeWrapV);

		for (int i = 0; i < buffers.size; i++) {
			buffers.get(i).rebind();
		}

		for (PostProcessorEffect e : effectsManager) {
			e.rebind();
		}
	}

	/** Stops capturing the scene and apply the effect chain, if there is one. If the specified output framebuffer is NULL, then the
	 * rendering will be performed to screen. */
	public void render (FrameBuffer dest) {
		captureEnd();

		if (!hasCaptured) {
			return;
		}

		// Array<PostProcessorEffect> items = manager.items;
		Array<PostProcessorEffect> items = enabledEffects;

		int count = items.size;
		if (count > 0) {

			Gdx.gl.glDisable(GL20.GL_CULL_FACE);
			Gdx.gl.glDisable(GL20.GL_DEPTH_TEST);

			// render effects chain, [0,n-1]
			if (count > 1) {
				for (int i = 0; i < count - 1; i++) {
					PostProcessorEffect e = items.get(i);

					composite.capture();
					{
						e.render(composite.getSourceBuffer(), composite.getResultBuffer());
					}
				}

				// complete
				composite.end();
			}

			if (listener != null && dest == null) {
				listener.beforeRenderToScreen();
			}

			// render with null dest (to screen)
			items.get(count - 1).render(composite.getResultBuffer(), dest);

			// ensure default texture unit #0 is active
			Gdx.gl.glActiveTexture(GL20.GL_TEXTURE0);
		} else {
			Gdx.app.log("PostProcessor", "No post-processor effects enabled, aborting render");
		}
	}

	/** Convenience method to render to screen. */
	public void render () {
		render(null);
	}

	private int buildEnabledEffectsList () {
		enabledEffects.clear();
		for (PostProcessorEffect e : effectsManager) {
			if (e.isEnabled()) {
				enabledEffects.add(e);
			}
		}

		return enabledEffects.size;
	}

	/** Restores the previously set viewport if one was specified earlier and the destination buffer is the screen */
	protected static void restoreViewport (FrameBuffer dest) {
		if (hasViewport && dest == null) {
			Gdx.gl.glViewport((int)viewport.x, (int)viewport.y, (int)viewport.width, (int)viewport.height);
		}
	}

}
