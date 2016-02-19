using Noesis.Javascript;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;

namespace ScriptEditor {
    public class ScriptReader {
        public JavascriptContext context = new JavascriptContext();
        public Script current;

        public ScriptReader() {
            context.SetParameter("p", this);
            context.SetParameter("MsgType", typeof(MsgType));
            context.SetParameter("RPGObject", typeof(RPGObject));
            context.SetParameter("BalloonType", typeof(BalloonType));
            context.Run("this.__proto__ = p");
        }


        public List<Script> read(string FileName) {
            if (!File.Exists(FileName))
                return null;

            var list = new List<Script>();

            using (var sr = new StreamReader(FileName, Encoding.UTF8)) {
                String line;
                while ((line = sr.ReadLine()) != null) {
                    current = new Script();
                    current.setScript(line);
                    try {
                        execute(current, line);
                    } catch (Exception) {
                        //throw;
                    }
                    list.Add(current);
                }
            }
            return list;
        }

        public void execute(Script self ,String js) {
            current = self;
            context.Run(js);
        }

        public void say(String str, String title) {
            current.translate = () => "[#Red]" + title + "[]说：[#Blue]" + str+"[]";
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
            current.translate = () => "[#DarkViolet]显示[][#Green]" + type.ToString() + "[]对话框";
        }

        public void setBalloon(BalloonType type) {
            current.translate = () => "[#Red]当前角色[]的心情是[#Green]" + type.ToString() + "[]";
        }

        public void setBalloon(Object npc,BalloonType type) {
            current.translate = () => "[#Red]"+npc+ "[]的心情是[#Green]" + type.ToString() + "[]";
        }

        public NPC getNPC(String id) {
            return new NPC(id);
        }
    }
}
