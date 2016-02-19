using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ItemEditor.Model
{
    public class SPELLCARD : ItemBase
    {
        public Double cost { get; set; }
        public String description2 { get; set; }
        public String range { get; set; }
        public String forward { get; set; }
        public Boolean trigger { get; set; }
        public Double success { get; set; }
        public Double animation { get; set; }
        public String deadable { get; set; }



        protected override void OnReading(Newtonsoft.Json.Linq.JObject json)
        {
            cost = json.GetSafeDoubleValue("cost");
            success = json.GetSafeDoubleValue("success");
            description2 = json.GetSafeStringValue("description2");
            range = json.GetSafeStringValue("range");
            forward = json.GetSafeStringValue("forward");
            if (json.GetValue("trigger") != null)
                trigger = bool.Parse(json.GetValue("trigger").ToString());
            else
                trigger = false;

            animation = json.GetSafeDoubleValue("animation");
            deadable = json.GetSafeStringValue("deadable");
            base.OnReading(json);
        }


    }

}
