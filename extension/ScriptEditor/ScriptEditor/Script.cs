using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Text;

namespace ScriptEditor {
    public class Script {
        private string script;
        public bool modify = false;
        private List<RenderString> renderList = new List<RenderString>();

        public Func<string> translate;

        public Script() {
            translate = getScript;
        }

        override public String ToString() {
            return translate();
        }

        public Boolean empty() {
            return translate == null;
        }

        public string getScript() {
            return script;
        }

        public void setScript(string script) {
            this.script = script;
            modify = true;
        }

        public List<RenderString> render() {
            if (modify) {
                renderList.Clear();

                String text = translate();

                RenderString rs = new RenderString();
                rs.brush = Brushes.Black;
                int skip = 0;

                for (var i = 0; i < text.Length; i++) {
                    if (skip > 0) {
                        skip--;
                        continue;
                    }

                    var c = text[i];
                    if (c == '[' && text[i + 1] == '#') {
                        for (var j = i; j < text.Length; j++) {
                            if (text[j] == ']') {
                                renderList.Add(rs);
                                rs = new RenderString();
                                rs.brush = Brushes.Black;
                                string color = text.Substring(i +2, j-i-2);
                                rs.brush = (Brush)typeof(Brushes).GetProperty(color).GetValue(null, null);
                                skip = color.Length + 2;
                                break;
                            }
                        }
                        continue;
                    }

                    if (c == '[' && text[i + 1] == ']') {
                        skip = 1;
                        renderList.Add(rs);
                        rs = new RenderString();
                        rs.brush = Brushes.Black;
                        continue;
                    }

                    rs.str += c;

                }

                renderList.Add(rs);

                modify = false;
            }


            return renderList;
        }

    }

    public class RenderString {
        public string str;
        public Brush brush;

    }

    public class NPC {
        public String name;

        public NPC(String name) {
            this.name = name;
        }

        public override string ToString() {
            return name;
        }
    }

}
