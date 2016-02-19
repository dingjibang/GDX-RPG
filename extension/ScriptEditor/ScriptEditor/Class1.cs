using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace ScriptEditor {
     public class Script {
        public String script;

        override public String ToString() {
            return translate();
        }

        public delegate string translateHandler();
        public event translateHandler translate;

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
