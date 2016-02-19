using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace ScriptEditor {
    public partial class ScriptEditForm : Form {
        public ScriptEditForm() {
            InitializeComponent();
        }

        private void ScriptEditForm_Load(object sender, EventArgs e) {

        }

        Script script;
        public void init(Script script) {
            this.script = script;
            this.textBox2.Text = script.getScript();
        }

        private void button1_Click(object sender, EventArgs e) {
            script.setScript(textBox2.Text);
            Close();
        }
    }
}
