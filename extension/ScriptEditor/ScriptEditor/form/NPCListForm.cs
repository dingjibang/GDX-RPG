using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace ScriptEditor.form {
    public partial class NPCListForm : Form {
        public NPCListForm() {
            InitializeComponent();
        }

        private void button2_Click(object sender, EventArgs e) {
            Close();
        }

        private void PauseForm_Load(object sender, EventArgs e) {
            listBox1.DataSource = NPC.list;
        }

        public Form init(Script script) {
            return this;
        }

        private void button1_Click(object sender, EventArgs e) {
            NPC.current = (NPC)listBox1.SelectedItem;
            Close();
        }

        private void listBox1_SelectedIndexChanged(object sender, EventArgs e) {

        }

        private void button3_Click(object sender, EventArgs e) {

        }
    }
}
