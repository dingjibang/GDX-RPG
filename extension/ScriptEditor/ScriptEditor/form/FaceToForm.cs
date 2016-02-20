using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace ScriptEditor.form {
    public partial class FaceToForm : Form {
        public FaceToForm() {
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
            var npc = script.param["npc"];
            if (npc == null) {
                radioButton1.Checked = true;
            } else {
                radioButton2.Checked = true;
                NPC.current = (NPC)npc;
            }

            var face = (RPGObject)script.param["face"];

            radioButton3.Checked = face == RPGObject.FACE_D;
            radioButton4.Checked = face == RPGObject.FACE_U;
            radioButton5.Checked = face == RPGObject.FACE_R;
            radioButton6.Checked = face == RPGObject.FACE_L;

            this.StartPosition = FormStartPosition.CenterParent;
            return this;
        }

        private void button1_Click(object sender, EventArgs e) {
            script.param["npc"] = radioButton1.Checked ? null : NPC.current;
            RPGObject face;
            if (radioButton3.Checked) face = RPGObject.FACE_D;
            else if (radioButton4.Checked) face = RPGObject.FACE_U;
            else if (radioButton5.Checked) face = RPGObject.FACE_R;
            else face = RPGObject.FACE_L;
            script.param["face"] = face;
            script.modify = true;
            Close();
        }

        private void radioButton2_CheckedChanged(object sender, EventArgs e) {

        }

        private void radioButton2_Click(object sender, EventArgs e) {
            NPCListForm form = new NPCListForm();
            form.ShowDialog();
        }
    }
}
