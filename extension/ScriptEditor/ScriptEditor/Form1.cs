using Noesis.Javascript;
using System;
using System.Collections.Generic;
using System.IO;
using System.Text;
using System.Windows.Forms;

namespace ScriptEditor {
    public partial class Form1 : Form {
        public JavascriptContext context = new JavascriptContext();
        public enum MsgType {莲子, 梅莉};
        public enum RPGObject { FACE_L, FACE_R, FACE_U, FACE_D }

        public Form1() {
            InitializeComponent();
        }
        private void Form1_Load(object sender, EventArgs e) {
            context.SetParameter("p", this);
            context.SetParameter("MsgType", typeof(MsgType));
            context.SetParameter("RPGObject", typeof(RPGObject));
            context.Run("this.__proto__ = p");

            read();

        }

        Script current;

        private void read() {
            StreamReader sr = new StreamReader("E:/Workspaces/MyEclipse 8.5/rpg/android/assets/script/map/s0-1.js", Encoding.UTF8);
            listBox.Items.Clear();

            String line;
            while ((line = sr.ReadLine()) != null) {

                current = new Script();
                current.script = line;
                try {
                context.Run(line);
                } catch (Exception) {
                    //throw;
                }
                if (current.empty())
                    current.translate += () => {
                        return line;
                    };

                listBox.Items.Add(current);
                
            }
            sr.Dispose();
        }

        public void say(String str,String title) {
            current.translate += () => {
                return "\""+title +"\"  说： "+str;
            };
        }

        public void move(int step) {
            current.translate += () => {
                return "当前角色移动了"+step+"步";
            };
        }

        public void move(NPC npc,int step) {
            current.translate += () => {
                return npc+"移动了" + step + "步";
            };
        }

        public void pause(int step) {
            current.translate += () => {
                return "暂停" + step + "帧";
            };
        }

        public void faceTo(RPGObject face) {
            current.translate += () => {
                return "当前角色转向至方向" + face;
            };
        }

        public void faceTo(Object obj,RPGObject face) {
            current.translate += () => {
                return obj+"转向至方向" + face;
            };
        }

        public void hideMSG() {
            current.translate += () => {
                return "隐藏对话框";
            };
        }

        public void showMSG(MsgType type) {
            current.translate += () => {
                return "显示"+type.ToString()+"对话框";
            };
        }

        public NPC getNPC(string name) {
            return new NPC(name);
        }
        
        private void 另存为ToolStripMenuItem_Click(object sender, EventArgs e) {

        }

       
    }

}
