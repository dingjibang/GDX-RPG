using Noesis.Javascript;
using System;
using System.Collections.Generic;
using System.Drawing;
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
            e.DrawBackground();
            Script script = (Script)listBox.Items[e.Index];

            Rectangle rec = new Rectangle(e.Bounds.X,e.Bounds.Y,e.Bounds.Width,e.Bounds.Height);

            List<RenderString> list = script.render();
            foreach (var rs in list) {
                e.Graphics.DrawString(rs.str, e.Font, rs.brush, rec);
                rec.X += (int)e.Graphics.MeasureString(rs.str, e.Font).Width;
            }
            
            e.DrawFocusRectangle();
        }

        private void listBox_SelectedIndexChanged(object sender, EventArgs e) {

        }
    }

}
