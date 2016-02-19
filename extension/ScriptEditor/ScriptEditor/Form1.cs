using Noesis.Javascript;
using System;
using System.Collections.Generic;
using System.Drawing;
using System.IO;
using System.Text;
using System.Windows.Forms;

namespace ScriptEditor {
    public partial class Form1 : Form {

        public static ScriptReader m_reader = new ScriptReader();

        public Form1() {
            InitializeComponent();
        } 
        private void Form1_Load(object sender, EventArgs e) {
            listBox.ItemHeight = 22;
            JSObject.reader = Form1.m_reader;
            read("s0-1.js");
        }

        private void read(string Filename) {
            listBox.DataSource = m_reader.read(Filename);
        }
        
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

        private void listBox_DrawItem(object sender, DrawItemEventArgs e) {
            if (listBox.Items.Count == 0){
                return;
            }
            e.DrawBackground();
            e.DrawFocusRectangle();
            Script script = (Script)listBox.Items[e.Index];
            float difH = (e.Bounds.Height - e.Font.Height) / 2;
            Rectangle rec = new Rectangle(e.Bounds.X,e.Bounds.Y + (int)difH,e.Bounds.Width,e.Font.Height);

            List<RenderString> list = script.render();
            foreach (var rs in list) {
                e.Graphics.DrawString(rs.str, e.Font, rs.brush, rec);
                rec.X += (int)e.Graphics.MeasureString(rs.str, e.Font).Width;
            }
            
        }

        private void listBox_SelectedIndexChanged(object sender, EventArgs e) {

        }

        private void listBox_MeasureItem(object sender, MeasureItemEventArgs e) {
        }

        private void listBox_DoubleClick(object sender, EventArgs e) {
            Script script = currentSelectScript();
            if (script == null) return;
            if (script.onClick != null)
                script.onClick();
        }

        private Script currentSelectScript() {
            var idx = listBox.SelectedIndex;
            if (idx < 0) return null;
            Script script = listBox.Items[idx] as Script;
            return script;
        }

        private void 编辑源文件ToolStripMenuItem_Click(object sender, EventArgs e) {
            Script script = currentSelectScript();
            if (script == null) return;
            var form = new ScriptEditForm();
            form.init(script);
            form.ShowDialog();
        }

        private void 上移ctrlupToolStripMenuItem_Click(object sender, EventArgs e) {

        }

        private void 编辑ToolStripMenuItem_Click(object sender, EventArgs e) {
            listBox_DoubleClick(null, null);
        }

        private void listBox_MouseDown(object sender, MouseEventArgs me) {
            if (me.Button == MouseButtons.Right) {//判断是否右键点击
                Point p = me.Location;//获取点击的位置
                int index = listBox.IndexFromPoint(p);//根据位置获取右键点击项的索引
                listBox.SelectedIndex = index;//设置该索引值对应的项为选定状态
                //checkedListBox1.SetItemChecked(index, true);//如果需要的话这句可以同时设置check状态
            }
        }

    }

}
