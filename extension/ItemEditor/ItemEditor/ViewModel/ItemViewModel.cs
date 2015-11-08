using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ItemEditor
{
    public class ItemViewModel : ViewModelBase
    {

        public string RootPath { get; set; }
        private string m_TempIconPath;
        public string TempIconPath { get { return m_TempIconPath; } set { m_TempIconPath = value; RaisePropertyChanged("IconPath"); } }

        public bool Editored { get; set; }
        public string IconPath
        {
            get
            {
                if (!string.IsNullOrEmpty(TempIconPath))
                {
                    return TempIconPath;
                }
                if (System.IO.File.Exists(RootPath + "/images/icons/i" + id + ".png"))
                    return RootPath + "/images/icons/i" + id + ".png";
                else
                    return RootPath + "/images/icons/i0.png";
            }
        }

        private Item m_Item;

        public Item Item
        {
            get { return m_Item; }
            set 
            { 
                m_Item = value;
                if (m_Item != null)
                {
                    this.id = value.id;
                    this.filePath = value.filePath;
                    this.realName = value.realName;
                    this.name = value.name;
                    ItemType type = ItemType.Item;
                    Enum.TryParse<ItemType>(value.type, true, out type);
                    this.type = type;
                    this.throwable = value.throwable;
                    this.illustration = value.illustration;
                    this.disable = value.disable;
                    this.use = value.use;
                    if (this.type == ItemType.Equipment)
                    {
                        this.illustration2 = value.illustration2;
                        this.onlyFor = value.onlyFor;
                        this.equipType = (equipType)Enum.Parse(typeof(equipType), value.equipType);
                        this.EquipmentProp = value.equipProp.newEquipmentPropViewModel();
                    }
                }
                else
                {
                    this.id = 0;
                    this.filePath = "";
                    this.realName = "";
                    this.name = "";
                    this.type = ItemType.Item;
                    this.throwable = false;
                    this.illustration = "";
                    this.disable = false;
                    this.use = "";
                }
                Editored = false;
            }
        }
        

        private int m_id;

        public int id
        {
            get { return m_id; }
            set
            {
                if (m_id == value) return;
                var OrgValue = m_id;
                m_id = value;
                OnPropertyChanged("id", OrgValue, value);
            }
        }

        private String m_name;

        public String name
        {
            get { return m_name; }
            set
            {
                if (m_name == value) return;
                var OrgValue = m_name;
                m_name = value;
                OnPropertyChanged("name", OrgValue, value);
            }
        }

        private string m_realName;

        public string realName
        {
            get { return m_realName; }
            set
            {
                if (m_realName == value) return;
                var OrgValue = m_realName;
                m_realName = value;
                OnPropertyChanged("realName", OrgValue, value);
            }
        }

        private String m_filePath;

        public String filePath
        {
            get { return m_filePath; }
            set
            {
                if (m_filePath == value) return;
                var OrgValue = m_filePath;
                m_filePath = value;
                OnPropertyChanged("filePath", OrgValue, value);
            }
        }

        private ItemType m_type;

        public ItemType type
        {
            get { return m_type; }
            set
            {
                if (m_type == value) return;
                var OrgValue = m_type;
                m_type = value;
                OnPropertyChanged("type", OrgValue, value);
            }
        }

        private bool m_throwable;

        public bool throwable
        {
            get { return m_throwable; }
            set
            {
                if (m_throwable == value) return;
                var OrgValue = m_throwable;
                m_throwable = value;
                OnPropertyChanged("throwable", OrgValue, value);
            }
        }


        private String m_illustration;

        public String illustration
        {
            get { return m_illustration; }
            set
            {
                if (m_illustration == value) return;
                var OrgValue = m_illustration;
                m_illustration = value;
                OnPropertyChanged("illustration", OrgValue, value);
            }
        }

        private bool m_disable;

        public bool disable
        {
            get { return m_disable; }
            set
            {
                if (m_disable == value) return;
                var OrgValue = m_disable;
                m_disable = value;
                OnPropertyChanged("disable", OrgValue, value);
            }
        }


        private String m_use;

        public String use
        {
            get { return m_use; }
            set
            {
                if (m_use == value) return;
                var OrgValue = m_use;
                m_use = value;
                OnPropertyChanged("use", OrgValue, value);
            }
        }

        private string m_onlyFor;

        public string onlyFor
        {
            get { return m_onlyFor; }
            set
            {
                if (m_onlyFor == value) return;
                var OrgValue = m_onlyFor;
                m_onlyFor = value;
                OnPropertyChanged("onlyFor", OrgValue, value);
            }
        }

        private string m_illustration2;

        public string illustration2
        {
            get { return m_illustration2; }
            set
            {
                if (m_illustration2 == value) return;
                var OrgValue = m_illustration2;
                m_illustration2 = value;
                OnPropertyChanged("illustration2", OrgValue, value);
            }
        }

        private equipType m_equipType;

        public equipType equipType
        {
            get { return m_equipType; }
            set
            {
                if (m_equipType == value) return;
                var OrgValue = m_equipType;
                m_equipType = value;
                OnPropertyChanged("equipType", OrgValue, value);
            }
        }

        private EquipmentPropViewModel m_EquipmentProp;
        public EquipmentPropViewModel EquipmentProp
        {
            get { return m_EquipmentProp; }
            set
            {
                if (m_EquipmentProp == value) return;
                var OrgValue = m_EquipmentProp;
                m_EquipmentProp = value;
                OnPropertyChanged("EquipmentProp", OrgValue, value);
            }
        }

        public void Save()
        {
            if (Editored)
            {
                RefreshToModel();
                m_Item.Save(RootPath);
                if (!string.IsNullOrEmpty(TempIconPath)
                    && System.IO.File.Exists(TempIconPath))
                {
                    System.IO.File.Copy(TempIconPath, RootPath + "\\images\\icons\\i" + id + ".png", true);
                    TempIconPath = null;
                }
            }
        }

        public void RefreshToModel()
        {
            if (m_Item != null)
            {
                m_Item.disable = this.disable;
                m_Item.filePath = this.filePath;
                m_Item.id = this.id;
                m_Item.illustration = this.illustration;
                m_Item.name = this.name;
                m_Item.realName = this.realName;
                m_Item.throwable = this.throwable;
                m_Item.type = this.type.ToString();
                m_Item.use = this.use;
                if (this.type == ItemType.Equipment)
                {
                    m_Item.illustration2 = this.illustration2;
                    m_Item.onlyFor = this.onlyFor;
                    m_Item.equipType = this.equipType.ToString();
                    m_Item.equipProp.SetValue( this.EquipmentProp);
                    m_Item.prop = m_Item.equipProp.ToJsonString();
                }
            }
        }

        protected override void OnPropertyChanged(string PropName, object Oldvalue, object NewValue)
        {
            Editored = true;
            base.OnPropertyChanged(PropName, Oldvalue, NewValue);
        }
    }
}
