using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace ScriptEditor {
     public class Script {
        public String script;

        public Func<string> translate { get; set; }

        override public String ToString() {
            if (translate == null)
            {
                return "";
            }
            return translate();
        }

        //public delegate string translateHandler();
        //public event translateHandler translate;

          
        public Boolean empty() {
            return translate == null;
        }

    }

    public class NPC{
        public String name;

        public NPC(String name) {
            this.name = name;
        }

        public override string ToString() {
            return name;
        }
    }
    
}
