using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ItemEditor
{
    public class ItemBase
    {

        public ItemBase()
        {
            packable = true;
        }

        public JObject orgObject { get; set; }

        //base
        public String name { get; set; }
        public String description { get; set; }
        public int id { get; set; }
        public String filePath { get; set; }
        public string type { get; set; }
        public bool throwable { get; set; }
        //public String illustration { get; set; }
        public bool disable { get; set; }

        public bool packable { get; set; }

        public double buy { get; set; }

        public double sell { get; set; }

        public ItemEditor.Model.Effect effect { get; set; }

        //public String use { get; set; }

        ////equipment
        //public string onlyFor { get; set; }
        //public string illustration2 { get; set; }
        //public string equipType { get; set; }
        //public string prop { get; set; }

        #region "Common"
        public double animation { get; set; }
        #endregion

        #region "Item"
        public String forward { get; set; }
        public String range { get; set; }
        public Boolean removeable { get; set; }
        public String deadable { get; set; }

        #endregion

        #region "Equipment"
        public string onlyFor { get; set; }
        public string description2 { get; set; }
        public string equipType { get; set; }
        #endregion

        #region"Spellcard Extend Item"
        public Double cost { get; set; }
        public Boolean trigger { get; set; }
        public Double success { get; set; }
        #endregion
        public void Read(string file)
        {
            using (StreamReader reader = File.OpenText(file))
            {
                filePath = file;
                id = int.Parse(Path.GetFileNameWithoutExtension(file));

                JObject json = JObject.Parse(reader.ReadToEnd());
                description = json.GetValue("name").ToString();
                name = description;
                type = json.GetValue("type").ToString();
                if (json.GetValue("disable") != null)
                    disable = bool.Parse(json.GetValue("disable").ToString());
                else
                    disable = false;

                if (json.GetValue("throwable") != null)
                    throwable = bool.Parse(json.GetValue("throwable").ToString());
                else
                    throwable = true;

                if (json.GetValue("packable") != null)
                    packable = bool.Parse(json.GetValue("packable").ToString());
                else
                    packable = true;
                buy = json.GetSafeDoubleValue("buy");
                sell = json.GetSafeDoubleValue("sell");
                //use = json.GetValue("use").ToString();
                description = json.GetValue("description").ToString();
                if (0 == string.Compare(type, ItemType.Equipment.ToString(), true))
                {
                    onlyFor = json.GetSafeStringValue("onlyFor");
                    description2 = json.GetSafeStringValue("description2");
                    equipType = json.GetSafeStringValue("equipType");
                }
                else
                {
                    forward = json.GetSafeStringValue("forward", "friend");
                    range = json.GetSafeStringValue("range","one");
                    if (json.GetValue("removeable") != null)
                        removeable = bool.Parse(json.GetValue("removeable").ToString());
                    else
                        removeable = true;
                    deadable = json.GetSafeStringValue("deadable", "no");
                    if (0 == string.Compare(type, ItemType.Spellcard.ToString(), true))
                    {
                        cost = json.GetSafeDoubleValue("cost");
                        if (json.GetValue("trigger") != null)
                            trigger = bool.Parse(json.GetValue("trigger").ToString());
                        else
                            trigger = false;
                        success = json.GetSafeDoubleValue("success");
                    }

                }
                animation = json.GetSafeDoubleValue("animation");

                this.effect = new Model.Effect();
                this.effect.parent = this;
                this.effect.Read(json.GetSafeStringValue("effect"));
                orgObject = json;
            }
        }

        protected virtual void OnReading(JObject json)
        {

        }

        protected virtual void OnSaving(JObject json)
        {
                        //if (0 == string.Compare(type, ItemType.Equipment.ToString(), true))
            //{
            //    json["onlyFor"]= onlyFor;
            //    json["illustration2"]= illustration2;
            //    json["equipType"]= equipType;
            //    json["prop"]= prop;
            //}
        }
        public void Remove(string RootPath)
        {
            if (!string.IsNullOrEmpty(filePath) && System.IO.File.Exists(filePath))
            {
                System.IO.File.Delete(filePath);
                if (System.IO.File.Exists(RootPath + "\\images\\icons\\i" + id + ".png"))
                    System.IO.File.Delete(RootPath + "\\images\\icons\\i" + id + ".png");
            }
        }

        public void Save(string RootPath)
        {
            string path = filePath;
            if (string.IsNullOrEmpty(path))
            {
                path = System.IO.Path.Combine(RootPath, "script\\data");
                path = System.IO.Path.Combine(path, id + ".grd");
                filePath = path;
            }
            JObject json = new JObject(orgObject);
            json["name"] = name;
            json["description"] = description;
            json["id"] = id;
            json["type"] = type;
            json["throwable"] = throwable;
            json["disable"] = disable;
            json["packable"] = packable;
            json["buy"] = buy;
            json["sell"] = sell;
            json["effect"] = effect.getJobject();
            json["animation"] = animation;

            json["onlyFor"] = onlyFor;
            json["description2"] = description2;
            json["equipType"] = equipType;

            //json["use"]  = use;
            if (0 == string.Compare(type, ItemType.Equipment.ToString(), true))
            {
                json["onlyFor"] = onlyFor;
                json["illustration2"] = description2;
                json["equipType"] = equipType;
            }
            else
            {
                json["forward"] = forward;
                json["range"] = range;
                json["removeable"] = removeable;
                json["deadable"] = deadable;
                if (0 == string.Compare(type, ItemType.Spellcard.ToString(), true))
                {
                    json["cost"] = cost;
                    json["trigger"] = trigger;
                    json["success"] = success;
                }
            }
            System.IO.File.WriteAllText(path, json.ToString(Newtonsoft.Json.Formatting.Indented));
        }
    }

    public class EffectProp
    {
        public string maxhp { get; set; }
        public string mapmp { get; set; }
        public string attack { get; set; }
        public string magicAttack { get; set; }
        public string defense { get; set; }
        public string magicDefense { get; set; }
        public string speed { get; set; }
        public string hit { get; set; }
        public string level { get; set; }
        public string exp { get; set; }
        public string hp { get; set; }
        public string mp { get; set; }
        public string maxsc { get; set; }
        public string dead { get; set; }


        public void ReadFromJson(string sjson)
        {
            Type type1 = typeof(EffectProp);
            if (string.IsNullOrEmpty(sjson))
            {
                
                foreach (var propInfo in type1.GetProperties())
                {
                    this.SetPropertyValue(propInfo.Name, "");
                }
                return;
            }
            JObject json = JObject.Parse(sjson);
            foreach (var propInfo in type1.GetProperties())
            {
                this.SetPropertyValue(propInfo.Name, json.GetSafeStringValue(propInfo.Name));
            }
        }

        public string ToJsonString()
        {
            JObject obj = new JObject();
            Type type1 = typeof(EffectProp);
            foreach (var propInfo in type1.GetProperties())
            {
                var value = this.GetPropertyValue(propInfo.Name);
                if (value is double)
                {
                    obj[propInfo.Name] = (Double)value;
                }
                else if (value is string)
                {
                    obj[propInfo.Name] = (string)value;
                }

            }
            return obj.ToString();
        }

        public EquipmentPropViewModel newEquipmentPropViewModel()
        {
            EquipmentPropViewModel item = new EquipmentPropViewModel();
            Type type1 = typeof(EffectProp);
            foreach (var propInfo in type1.GetProperties())
            {
                item.SetPropertyValue(propInfo.Name, propInfo.GetValue(this));
            }
            return item;
        }

        public void SetValue(EquipmentPropViewModel item)
        {
            Type type1 = typeof(EffectProp);
            foreach (var propInfo in type1.GetProperties())
            {
                this.SetPropertyValue(propInfo.Name, item.GetPropertyValue(propInfo.Name));
            }
        }
    }

    public static class JObjectExtension
    {
        public static string GetSafeStringValue(this JObject obj, string propName)
        {
            var o = obj.GetValue(propName);
            if (o == null)
            {
                return null;
            }
            return o.ToString();
        }

        public static string GetSafeStringValue(this JObject obj, string propName,string defaultvalue)
        {
            var o = obj.GetValue(propName);
            if (o == null)
            {
                return defaultvalue;
            }
            return o.ToString();
        }

        public static Double GetSafeDoubleValue(this JObject obj, string propName)
        {
            var o = obj.GetSafeStringValue(propName);
            if (o == null)
            {
                return 0;
            }
            double ret = 0;
            if (!double.TryParse(o, out ret))
            {
                return 0;
            }
            return ret;
        }
    }
}
