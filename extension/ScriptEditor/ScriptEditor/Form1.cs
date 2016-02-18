using Noesis.Javascript;
using System;
using System.Collections.Generic;
using System.Windows.Forms;

namespace ScriptEditor {
    public partial class Form1 : Form {
        public Form1() {
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e) {
            var context = new JavascriptContext();
            context.Run(@"var data = {msg:""中文fuckc""}");
            context.SetParameter("p", this);
            context.Run("this.__proto__ = p");
            //context.Run(@"data.msg = ""caonima""");
            Console.WriteLine((context.GetParameter("data") as Dictionary<string,object>)["msg"]);

            context.Run("execu('asd')");

        }

        public void execu(String str) {
            Console.Write(str);
        }

        private void 另存为ToolStripMenuItem_Click(object sender, EventArgs e) {

        }
    }
}
