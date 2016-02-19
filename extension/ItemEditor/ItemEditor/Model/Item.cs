using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ItemEditor.Model
{
    public class Item:ItemBase
    {
        public String forward { get; set; }
        public String range { get; set; }
        public Boolean removeable { get; set; }
        public String deadable { get; set; }
        public double animation { get; set; }
        protected override void OnReading(Newtonsoft.Json.Linq.JObject json)
        {
            forward = json.GetSafeStringValue("forward");
            range = json.GetSafeStringValue("range");
            if (json.GetValue("removeable") != null)
                removeable = bool.Parse(json.GetValue("removeable").ToString());
            else
                removeable = false;
            deadable = json.GetSafeStringValue("deadable");
            animation = json.GetSafeDoubleValue("animation");
            //prop = json.GetSafeStringValue("prop");
            //this.equipProp = new EquipmentProp();
            //this.equipProp.ReadFromJson(prop);
            base.OnReading(json);
        }
    }
}
