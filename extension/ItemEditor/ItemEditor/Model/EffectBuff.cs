using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ItemEditor.Model
{
    public class EffectBuff
    {
        public string type { get; set; }
        public int buff { get; set; }

        public EffectBuff Clone()
        {
            return new EffectBuff() { type = this.type, buff = this.buff };
        }

        public JObject GetJObject()
        {
            var jobj = new JObject();
            jobj["type"] = type;
            jobj["buff"] = buff;
            return jobj;
        }
    }
}
