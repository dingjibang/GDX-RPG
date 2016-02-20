using Microsoft.ClearScript.V8;
using ScriptEditor.form;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;

namespace ScriptEditor {
    public class ScriptReader { 
        public V8ScriptEngine engine = new V8ScriptEngine();
        public Script current;
        public CGClass CG = new CGClass();

        public ScriptReader() {
            engine.AddHostObject("prototype__________", this);
            engine.AddHostType("MsgType", typeof(MsgType));
            engine.AddHostType("RPGObject", typeof(RPGObject));
            engine.AddHostType("BalloonType", typeof(BalloonType));
            engine.AddHostObject("CG", CG);
            engine.Execute("this.__proto__ = prototype__________");
        }


        public List<Script> read(string FileName) {
            var list = new List<Script>();

            if (!File.Exists(FileName))
                return list;


            using (var sr = new StreamReader(FileName, Encoding.UTF8)) {
                String line;
                while ((line = sr.ReadLine()) != null) {
                    current = new Script();
                    current.setScript(line);
                    list.Add(current);
                }
            }
            return list;
        }

        public void execute(Script self ,String js) {
            current = self;
            engine.Execute(js);
        }

        public void say(String str, String title) {
            Script s = current;//日你妈这叫闭包？？什么迷之变量引用
            s.param["title"] = title;
            s.param["content"] = str;
            s.translate = () => "[#Red]" + (s.param["title"] as string == "" ? "自言自语" : s.param["title"]) + "[]说：[#Blue]" + s.param["content"] + "[]";
            s.onClick = () => {
                var form2 = new SayForm().init(s);
                form2.ShowDialog();
            };
            s.getScript = () => "say(\"" + s.param["content"]+"\",\""+s.param["title"]+"\");";
        }

        public void say(String str) {
            say(str, "");
        }

        public void setKeyLocker(bool flag) {
            Script s = current;
            s.param["flag"] = flag;
            s.translate = () => "将键盘" + ((bool)s.param["flag"] ? "" : "[#DarkViolet]解除[]") + "[#DarkViolet]锁定[]";
            s.onClick = () => {
                var form = new SetKeyLockerForm().init(s);
                form.ShowDialog();
            };
            s.getScript = () => "setKeyLocker(" + s.param["flag"].ToString().ToLower() + ");";
        }

        public void setRenderAble(bool flag) {
            Script s = current;
            s.param["flag"] = flag;
            s.translate = () => ((bool)s.param["flag"] ? "[#DarkViolet]开始[]" : "[#DarkViolet]关闭[]") + "画面渲染";
            s.onClick = () => {
                var form = new RenderAbleForm().init(s);
                form.ShowDialog();
            };
            s.getScript = () => "setRenderAble(" + s.param["flag"].ToString().ToLower() + ");";
        }

        public void move(int step) {
            current.translate = () => "[#Red]当前角色[]移动了[#Blue]" + step + "[]步";
        }

        public void move(NPC npc, int step) {
            current.translate = () => "[#Red]"+npc + "[]移动了[#Blue]" + step + "[]步";
        }

        public void pause(int step) {
            Script s = current;
            s.param["time"] = step;
            s.translate = () => "暂停[#Blue]" + s.param["time"] + "[]帧";
            s.onClick = () => {
                var form = new PauseForm().init(s);
                form.ShowDialog();
            };
            s.getScript = () => "pause(" + s.param["time"]+");";
        }

        public void faceTo(RPGObject face) {
            faceTo(null, face);
        }

        private String getFaceName(RPGObject face) {
            switch (face) {
                case RPGObject.FACE_D: return "下方";
                case RPGObject.FACE_L: return "左侧";
                case RPGObject.FACE_R: return "右侧";
                case RPGObject.FACE_U: return "上方";
            }
            return face.ToString();
        }

        public void faceTo(Object obj, RPGObject face) {
            Script s = current;
            s.param["npc"] = obj;
            s.param["face"] = face;
            s.translate = () => (s.param["npc"] == null ? "[#Red]当前角色[]" : "[#Red]" + obj + "[]")+ "面朝向至[#Green]" + getFaceName((RPGObject)s.param["face"]) + "[]";
            s.onClick = () => {
                var form = new FaceToForm().init(s);
                form.ShowDialog();
            };
            s.getScript = () => "faceTo(" + (s.param["npc"] == null ? "" : (((NPC)s.param["npc"]).varName + ","))+"RPGObject." + s.param["face"] + ");";
        }

        public void hideMSG() {
            current.translate = () => "[#DarkViolet]隐藏[]对话框";
        }

        public void showMSG(MsgType type) {
            current.translate = () => "[#DarkViolet]显示[][#Green]" + type + "[]对话框";
        }

        public void setBalloon(BalloonType type) {
            current.translate = () => "[#Red]当前角色[]的心情是[#Green]" + type.ToString() + "[]";
        }

        public void setBalloon(Object npc,BalloonType type) {
            current.translate = () => "[#Red]"+npc+ "[]的心情是[#Green]" + type.ToString() + "[]";
        }

        public void stopAllSE(int time) {
            current.translate = () => "[#Blue]" + time + "[]毫秒淡出所有的[#DarkViolet]音效[]";
        }

        public void playSE(string file) {
             Script s = current;
            s.param["time"] = file;
            s.translate = () => "播放音效文件：[#Blue]" + s.param["time"] + "[]";
            s.onClick = () => {
                var form = new PlaySEForm().init(s);
                form.ShowDialog();
            };
            s.getScript = () => "playSE(\"" + s.param["time"]+"\");";
        }

        public NPC getNPC(String id) {
            return new NPC(id,current.script.Split('=')[0],current);
        }

        public void end() {
            current.translate = () => "[#DarkViolet]结束脚本运行[]";
        }

        public void removeSelf() {
            current.translate = () => "[#DarkViolet]删除自身[]";
        }

        public void teleport(string map,int x,int y,int z) {
            current.translate = () => "[#DarkViolet]传送至地图[][#Blue]"+map+"[](x:[#Blue]"+x+ "[],y:[#Blue]" + y+ "[],z:[#Blue]" + z+"[])";
        }

        public void setCameraPositionWithHero(int x,int y,bool wait) {
            current.translate = () => "摄像头位置偏移至 [x:[#Blue]" + x + "[], y:[#Blue]" + y + "[]]("+(wait?"":"不")+"等待执行结束)";
        }

    }
}
