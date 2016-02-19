using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace ScriptEditor {
    public partial class SayForm : Form {
        public SayForm() {
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e) {
            script.param["title"] = this.textBox1.Text;
            script.param["content"] = this.textBox2.Text;
            script.modify = true;
            Close();
        }

        Script script;
        public SayForm init(Script script) {
            this.script = script;
            this.textBox1.Text = script.param["title"] as string;
            this.textBox2.Text = script.param["content"] as string;
            this.StartPosition = FormStartPosition.CenterParent;
            return this;
        }

        private void SayForm_Load(object sender, EventArgs e) {

        }

        private void button2_Click(object sender, EventArgs e) {
            Close();
        }
    }
}
