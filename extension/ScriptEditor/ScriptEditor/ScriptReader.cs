using Microsoft.ClearScript.V8;
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
            current.translate = () => "将键盘"+(flag ? "" : "[#DarkViolet]解除[]") + "[#DarkViolet]锁定[]";
        }

        public void setRenderAble(bool flag) {
            current.translate = () => (flag ? "[#DarkViolet]开始[]" : "[#DarkViolet]关闭[]") + "画面渲染";
        }

        public void move(int step) {
            current.translate = () => "[#Red]当前角色[]移动了[#Blue]" + step + "[]步";
        }

        public void move(NPC npc, int step) {
            current.translate = () => "[#Red]"+npc + "[]移动了[#Blue]" + step + "[]步";
        }

        public void pause(int step) {
            current.translate = () => "暂停[#Blue]" + step + "[]帧";
        }

        public void faceTo(RPGObject face) {
            current.translate = () => "[#Red]当前角色[]面朝向至[#Green]" + getFaceName(face)+"[]";
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
            current.translate = () => "[#Red]"+obj + "[]面朝向至[#Green]" + getFaceName(face)+"[]";
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
            current.translate = () => "播放音效文件：[#Blue]" + file + "[]";
        }

        public NPC getNPC(String id) {
            return new NPC(id);
        }

    }
}
