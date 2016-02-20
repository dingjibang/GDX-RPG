using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace ScriptEditor {
    public enum RPGObject { FACE_L, FACE_R, FACE_U, FACE_D }
    public enum MsgType { 莲子, 梅莉 ,正常};
    public enum BalloonType { 沉默, 惊讶, 灵感, 汗 };

    public class CGClass : JSObject {
        public void disposeAll() {
            reader.current.translate = () => "销毁所有CG";
        }

        public void dispose(Object obj) {
            reader.current.translate = () => "销毁CG";
        }
    }

    public abstract class JSObject {
        public static ScriptReader reader;
    }
}
