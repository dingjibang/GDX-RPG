using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace ScriptEditor {
    public partial class SetKeyLockerForm : Form {
        public SetKeyLockerForm() {
            InitializeComponent();
        }

        private void Form2_Load(object sender, EventArgs e) {

        }

        Script script;
        public Form init(Script script) {
            this.script = script;
            this.checkBox1.Checked = (bool)script.param["flag"];
            this.StartPosition = FormStartPosition.CenterParent;
            return this;
        }

        private void button2_Click(object sender, EventArgs e) {
            Close();
        }

        private void button1_Click_1(object sender, EventArgs e) {
            script.param["flag"] = this.checkBox1.Checked;
            script.modify = true;
            Close();
        }
    }
}
