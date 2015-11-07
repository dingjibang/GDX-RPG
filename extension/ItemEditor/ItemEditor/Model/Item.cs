using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ItemEditor
{
    public class Item
    {
        public JObject orgObject { get; set; }

        //base
        public String name { get; set; }
        public String realName { get; set; }
        public int id { get; set; }
        public String filePath { get; set; }
        public string type { get; set; }
        public bool throwable { get; set; }
        public String illustration { get; set; }
        public bool disable { get; set; }
        public String use { get; set; }

        //equipment
        public string onlyFor { get; set; }
        public string illustration2 { get; set; }
        public string equipType { get; set; }
        public string prop { get; set; }

        public EquipmentProp equipProp { get; set; }

        public void Read(string file)
        {
            using (StreamReader reader = File.OpenText(file))
            {
                filePath = file;
                id = int.Parse(Path.GetFileNameWithoutExtension(file));

                JObject json = JObject.Parse(reader.ReadToEnd());
                realName = json.GetValue("name").ToString();
                name = realName;
                type = json.GetValue("type").ToString();
                if (json.GetValue("disable") != null)
                    disable = bool.Parse(json.GetValue("disable").ToString());
                else
                    disable = false;
                use = json.GetValue("use").ToString();
                illustration = json.GetValue("illustration").ToString();
                if (0 == string.Compare(type,ItemType.Equipment.ToString(),true))
                {
                    onlyFor = json.GetSafeStringValue("onlyFor");
                    illustration2 = json.GetSafeStringValue("illustration2");
                    equipType = json.GetSafeStringValue("equipType");
                    prop = json.GetSafeStringValue("prop");
                    this.equipProp = new EquipmentProp();
                    this.equipProp.ReadFromJson(prop);
                }
                orgObject = json;
            }
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
                path = System.IO.Path.Combine(RootPath ,"script\\data");
                path = System.IO.Path.Combine(path, id + ".grd");
                filePath = path;
            }
            JObject json = new JObject(orgObject);
            json["name"]=name;
            json["id"] =id;
            json["type"] = type;
            json["throwable"] = throwable;
            json["illustration"]  = illustration;
            json["disable"] = disable;
            json["use"]  = use;
            if (0 == string.Compare(type, ItemType.Equipment.ToString(), true))
            {
                json["onlyFor"]= onlyFor;
                json["illustration2"]= illustration2;
                json["equipType"]= equipType;
                json["prop"]= prop;
            }
            System.IO.File.WriteAllText( path, json.ToString(Newtonsoft.Json.Formatting.Indented));
        }
    }

    public class EquipmentProp
    {
        public Double maxhp { get; set; }
        public Double mapmp { get; set; }
        public Double attack { get; set; }
        public Double magicAttack { get; set; }
        public Double defense { get; set; }
        public Double magicDefense { get; set; }
        public Double speed { get; set; }
        public Double hit { get; set; }


        public void ReadFromJson(string sjson)
        {
            if (string.IsNullOrEmpty(sjson))
            {
                maxhp = 0;
                mapmp = 0;
                attack = 0;
                magicAttack = 0;
                defense =0;
                magicDefense = 0;
                speed = 0;
                hit = 0;
                return;
            }
            JObject json = JObject.Parse(sjson);
            maxhp = json.GetSafeDoubleValue("maxhp");
            mapmp = json.GetSafeDoubleValue("mapmp");
            attack = json.GetSafeDoubleValue("attack");
            magicAttack = json.GetSafeDoubleValue("magicAttack");
            defense = json.GetSafeDoubleValue("defense");
            magicDefense = json.GetSafeDoubleValue("magicDefense");
            speed = json.GetSafeDoubleValue("speed");
            hit = json.GetSafeDoubleValue("hit");
        }

        public string ToJsonString()
        {
            JObject obj = new JObject();
            Type type1 = typeof(EquipmentProp);
            foreach (var propInfo in type1.GetProperties())
            {
                var value = this.GetPropertyValue(propInfo.Name);
                if (value is double)
                {
                    obj[propInfo.Name] = (Double)value;
                }
                else if(value is string)
                {
                    obj[propInfo.Name] = (string)value;
                }
                
            }
            return obj.ToString();
        }

        public EquipmentPropViewModel newEquipmentPropViewModel()
        {
            EquipmentPropViewModel item = new EquipmentPropViewModel();
            Type type1 = typeof(EquipmentProp);
            foreach (var propInfo in type1.GetProperties())
            {
                item.SetPropertyValue(propInfo.Name,propInfo.GetValue(this));
            }
            return item;
        }

        public void SetValue(EquipmentPropViewModel item)
        {
            Type type1 = typeof(EquipmentProp);
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
