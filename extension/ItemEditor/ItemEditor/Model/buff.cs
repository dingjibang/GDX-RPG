using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ItemEditor
{
    public class Buff:ViewModelBase
    {
        public Buff()
        {
            data = new JObject();
            prop = new EffectProp();
            Editored = true;
        }
        public Buff(string filename )
        {
            this.filename = filename;

            data = JObject.Parse(System.IO.File.ReadAllText(filename));
            prop = new EffectProp();
            prop.ReadFromJson(data.GetSafeStringValue("prop"));
            data.PropertyChanged += (sender, e) => Editored = true;

            id = Int32.Parse(System.IO.Path.GetFileNameWithoutExtension(filename));
        }

        public string filename { get; set; }
        public JObject data { get; set; }
        public EffectProp prop { get; set; }

        public int id { get; internal set; }

        private string m_TempIconPath;
        public string TempIconPath { get { return m_TempIconPath; } set { m_TempIconPath = value; RaisePropertyChanged("IconPath"); } }


        public string RootPath { get; set; }

        public string IconPath
        {
            get
            {
                if (!string.IsNullOrEmpty(TempIconPath))
                {
                    return TempIconPath;
                }
                if (System.IO.File.Exists(RootPath + "/images/icons/b" + id + ".png"))
                    return RootPath + "/images/icons/b" + id + ".png";
                else
                    return RootPath + "/images/icons/b0.png";
            }
        }

        public void Save()
        {
            if (Editored || prop.Editor)
            { 
                data["prop"] = prop.GetJObejct();
                System.IO.File.WriteAllText(filename, data.ToString(Newtonsoft.Json.Formatting.Indented));
            }
        }
        public void Remove()
        {
            if (!string.IsNullOrEmpty(filename) && System.IO.File.Exists(filename))
            {
                System.IO.File.Delete(filename);
                if (System.IO.File.Exists(RootPath + "\\images\\icons\\b" + id + ".png"))
                    System.IO.File.Delete(RootPath + "\\images\\icons\\b" + id + ".png");
            }
        }
    }

    //public class Buff
    //{
    //    public Buff()
    //    {

    //    }
    //    public int id;
    //    // public string icon;
    //    public string name;
    //    public string description;
    //    public string type;
    //    public EffectProp prop;

    //    public int turn;

    //    public string orgjsonStr;

    //    public string orgPath;

    //    public void Read(string sPath)
    //    {
    //        string jsonStr = System.IO.File.ReadAllText(sPath);
    //        orgPath = sPath;
    //        id = Int32.Parse(System.IO.Path.GetFileNameWithoutExtension(sPath));
    //        orgjsonStr = jsonStr;
    //        JObject jorg = JObject.Parse(jsonStr);
    //        name = jorg.GetSafeStringValue("name");
    //        description = jorg.GetSafeStringValue("description");
    //        type = jorg.GetSafeStringValue("type");
    //        prop = new EffectProp();
    //        prop.ReadFromJson(jorg.GetSafeStringValue("prop"));
    //        turn = (int)jorg.GetSafeDoubleValue("turn");
    //    }


    //    public JObject getJObject()
    //    {

    //        JObject jorg = JObject.Parse(orgjsonStr);

    //        jorg["name"] = name;
    //        jorg["description"] = description;
    //        jorg["type"] = type;

    //        jorg["prop"] = prop.ToJsonString();
    //        jorg["turn"] = turn;
    //        return jorg;
    //    }

    //}
}
