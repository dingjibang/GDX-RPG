using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ItemEditor.Model
{
    public class Effect
    {
        public JObject orgObject { get; set; }

        public ItemBase parent { get; set; }

        public string orgProp { get; set; }
        public EffectProp prop { get; set; } 
        public void Read(string jsonStr)
        {
            orgObject = JObject.Parse(jsonStr);
            prop = new EffectProp();
            orgProp = orgObject.GetSafeStringValue("prop");
            prop.ReadFromJson(orgProp);
        }

        public JObject getJobject()
        {
            JObject jo = new JObject(orgObject);
            jo["prop"] = new JObject(prop.ToJsonString());
            return jo;
        }

        public override string ToString()
        {
            JObject jo = new JObject(orgObject);
            jo["prop"] = new JObject(prop.ToJsonString());
            return jo.ToString();
        }
    }

    public class BuffItem
    {
        public string type{get;set;}
        public double buff { get; set; }
        public double turn { get; set; } 
    }

    public class Buff
    {
        public JObject orgObject { get; set; }

        public int id { get; set; }
        public string name { get; set; }
        public string description { get; set; }
        public string type { get; set; }
        public string orgProp { get; set; }
        public EffectProp prop { get; set; }

        public void Read(string jsonStr)
        {
            orgObject = JObject.Parse(jsonStr);
        }
    }

}
