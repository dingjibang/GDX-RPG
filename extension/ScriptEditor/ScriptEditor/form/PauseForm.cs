using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace ScriptEditor.form {
    public partial class PauseForm : Form {
        public PauseForm() {
            InitializeComponent();
        }

        private void button2_Click(object sender, EventArgs e) {
            Close();
        }

        private void PauseForm_Load(object sender, EventArgs e) {

        }

        Script script;
        public Form init(Script script) {
            this.script = script;
            this.textBox1.Text = script.param["time"] + "";
            this.StartPosition = FormStartPosition.CenterParent;
            return this;
        }

        private void button1_Click(object sender, EventArgs e) {
            script.param["time"] = this.textBox1.Text;
            script.modify = true;
            Close();
        }
    }
}
