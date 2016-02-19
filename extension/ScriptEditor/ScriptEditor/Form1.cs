using Noesis.Javascript;
using System;
using System.Collections.Generic;
using System.IO;
using System.Text;
using System.Windows.Forms;

namespace ScriptEditor {
    public partial class Form1 : Form {


        public ScriptReader m_reader = new ScriptReader();
        public Form1() {
            InitializeComponent();
        }
        private void Form1_Load(object sender, EventArgs e) {
            read("E:/Workspaces/MyEclipse 8.5/rpg/android/assets/script/map/s0-1.js");
        }

        Script current;

        private void read(string Filename) {
            m_reader.read(Filename);
            listBox.DataSource = m_reader.getCurrentList();
        }

        //public void say(String str,String title) {
        //    current.translate = () => "\"" + title + "\"  说： " + str;
        //}

        //public void move(int step) {
        //    current.translate = () => "当前角色移动了"+step+"步";

        //}

        //public void move(NPC npc,int step) {
        //    current.translate = () => npc+"移动了" + step + "步";
        //}

        //public void pause(int step) {
        //    current.translate = () => "暂停" + step + "帧";
        //}

        //public void faceTo(RPGObject face) {
        //    current.translate = () =>  "当前角色转向至方向" + face;
        //}

        //public void faceTo(Object obj,RPGObject face) {
        //    current.translate = () => obj+"转向至方向" + face;
        //}

        //public void hideMSG() {
        //    current.translate = () => "隐藏对话框";
        //}

        //public void showMSG(MsgType type) {
        //    current.translate = () => "显示"+type.ToString()+"对话框";
        //}

        //public NPC getNPC(string name) {
        //    return new NPC(name);
        //}
        
        private void 另存为ToolStripMenuItem_Click(object sender, EventArgs e) {

        }

        private void 打开ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            using (OpenFileDialog dlg = new OpenFileDialog())
            {
                if (dlg.ShowDialog()== System.Windows.Forms.DialogResult.OK)
                {
                    read(dlg.FileName);
                }
            }
        }

       
    }

}
