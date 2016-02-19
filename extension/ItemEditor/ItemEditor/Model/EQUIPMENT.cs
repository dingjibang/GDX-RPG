using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ItemEditor.Model
{
    public class Equipment:ItemBase
    {
        public string onlyFor { get; set; }
        public string description2 { get; set; }
        public string equipType { get; set; }
        public double animation { get; set; }


        protected override void OnReading(Newtonsoft.Json.Linq.JObject json)
        {
            onlyFor = json.GetSafeStringValue("onlyFor");
            description2 = json.GetSafeStringValue("description2");
            equipType = json.GetSafeStringValue("equipType");
            //prop = json.GetSafeStringValue("prop");
            //this.equipProp = new EquipmentProp();
            //this.equipProp.ReadFromJson(prop);
            base.OnReading(json);
        }
    }
}
