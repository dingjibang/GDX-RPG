using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ItemEditor
{
    public class Buff
    {
        public Buff()
        {

        }
        public int id;
        // public string icon;
        public string name;
        public string description;
        public string type;
        public EffectProp prop;

        public int Turn;

        public string orgjsonStr;

        public string orgPath;

        public void Read(string sPath)
        {
            string jsonStr = System.IO.File.ReadAllText(sPath);
            orgPath = sPath;
            id = Int32.Parse(System.IO.Path.GetFileNameWithoutExtension(sPath));
            orgjsonStr = jsonStr;
            JObject jorg = JObject.Parse(jsonStr);
            name = jorg.GetSafeStringValue("name");
            description = jorg.GetSafeStringValue("description");
            type = jorg.GetSafeStringValue("type");
            prop = new EffectProp();
            prop.ReadFromJson(jorg.GetSafeStringValue("prop"));
            Turn = (int)jorg.GetSafeDoubleValue("Turn");
        }


        public JObject getJObject()
        {

            JObject jorg = JObject.Parse(orgjsonStr);

            jorg["name"] = name;
            jorg["description"] = description;
            jorg["type"] = type;

            jorg["prop"] = prop.ToJsonString();
            jorg["Turn"] = Turn;
            return jorg;
        }

    }
}
