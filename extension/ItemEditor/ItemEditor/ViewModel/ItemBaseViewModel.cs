using ItemEditor.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ItemEditor
{
    public class ItemBaseViewModel : ViewModelBase
    {

        public string RootPath { get; set; }
        private string m_TempIconPath;
        public string TempIconPath { get { return m_TempIconPath; } set { m_TempIconPath = value; RaisePropertyChanged("IconPath"); } }


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

        private ItemBase m_Item;

        public ItemBase Item
        {
            get { return m_Item; }
            set
            {
                m_Item = value;
                if (m_Item != null)
                {

                    this.name = value.name;
                    this.description = value.description;
                    this.id = value.id;
                    this.filePath = value.filePath;
                    this.throwable = value.throwable;
                    this.disable = value.disable;
                    this.packable = value.packable;
                    this.buy = value.buy;
                    this.sell = value.sell;
                    this.effect = value.effect;
                    this.animation = value.animation;
                    forwardType forwardType = forwardType.enemy;
                    Enum.TryParse<forwardType>(value.forward, true, out forwardType);
                    this.forward = forwardType;
                    rangeType rangeType = rangeType.one;
                    Enum.TryParse<rangeType>(value.range, true, out rangeType);
                    this.range = rangeType;
                    this.removeable = value.removeable;
                    deadableType deadableType = deadableType.no;
                    Enum.TryParse<deadableType>(value.deadable, true, out deadableType);
                    this.deadable = deadableType;
                    this.onlyFor = value.onlyFor;
                    this.description2 = value.description2;
                    ItemType type = ItemType.Item;
                    Enum.TryParse<ItemType>(value.type, true, out type);
                    this.cost = value.cost;
                    this.trigger = value.trigger;
                    this.success = value.success;
                    this.id = value.id;
                    this.filePath = value.filePath;
                    this.description = value.description;
                    this.name = value.name;
                    string stype = value.type;
                    ItemType tmp = ItemType.Item;
                    Enum.TryParse<ItemType>(stype, out tmp);
                    this.type = tmp;
                    this.throwable = value.throwable;
                    this.disable = value.disable;

                    if (type == ItemType.Equipment)
                    {
                        string sequipType = value.type;
                        equipType tmpType = equipType.EQUIP_CLOTHES;
                        Enum.TryParse<equipType>(sequipType, out tmpType);
                        this.equipType = tmpType;
                        this.EquipmentProp = this.effect.prop;
                        m_buffs.Clear();
                        value.effect.buffs.ForEach(buff => m_buffs.Add(buff.Clone()));
                    }
                    else if (type == ItemType.Spellcard)
                    {
                        this.EquipmentProp = this.effect.prop;
                        m_buffs.Clear();
                        value.effect.buffs.ForEach(buff => m_buffs.Add(buff.Clone()));
                    }
                }
                else
                {
                }
                Editored = false;
            }
        }

        public System.Collections.ObjectModel.ObservableCollection<EffectBuff> m_buffs = new System.Collections.ObjectModel.ObservableCollection<EffectBuff>();

        public System.Collections.ObjectModel.ObservableCollection<EffectBuff> Buffs
        {
            get { return m_buffs; }
            set
            {
                if (m_buffs == value) return;
                var OrgValue = m_buffs;
                m_buffs = value;
                OnPropertyChanged("Buffs", OrgValue, value);
            }
        }

        private EffectProp m_EquipmentProp;

        public EffectProp EquipmentProp
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

        private String m_description;
        public String description
        {
            get { return m_description; }
            set
            {
                if (m_description == value) return;
                var OrgValue = m_description;
                m_description = value;
                OnPropertyChanged("description", OrgValue, value);
            }
        }

        private Int32 m_id;
        public Int32 id
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

        private Boolean m_throwable;
        public Boolean throwable
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

        private Boolean m_disable;
        public Boolean disable
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

        private Boolean m_packable;
        public Boolean packable
        {
            get { return m_packable; }
            set
            {
                if (m_packable == value) return;
                var OrgValue = m_packable;
                m_packable = value;
                OnPropertyChanged("packable", OrgValue, value);
            }
        }

        private Double m_buy;
        public Double buy
        {
            get { return m_buy; }
            set
            {
                if (m_buy == value) return;
                var OrgValue = m_buy;
                m_buy = value;
                OnPropertyChanged("buy", OrgValue, value);
            }
        }

        private Double m_sell;
        public Double sell
        {
            get { return m_sell; }
            set
            {
                if (m_sell == value) return;
                var OrgValue = m_sell;
                m_sell = value;
                OnPropertyChanged("sell", OrgValue, value);
            }
        }

        private Effect m_effect;
        public Effect effect
        {
            get { return m_effect; }
            set
            {
                if (m_effect == value) return;
                var OrgValue = m_effect;
                m_effect = value;
                OnPropertyChanged("effect", OrgValue, value);
            }
        }

        private Double m_animation;
        public Double animation
        {
            get { return m_animation; }
            set
            {
                if (m_animation == value) return;
                var OrgValue = m_animation;
                m_animation = value;
                OnPropertyChanged("animation", OrgValue, value);
            }
        }

        private forwardType m_forward;
        public forwardType forward
        {
            get { return m_forward; }
            set
            {
                if (m_forward == value) return;
                var OrgValue = m_forward;
                m_forward = value;
                OnPropertyChanged("forward", OrgValue, value);
            }
        }

        private rangeType m_range;
        public rangeType range
        {
            get { return m_range; }
            set
            {
                if (m_range == value) return;
                var OrgValue = m_range;
                m_range = value;
                OnPropertyChanged("range", OrgValue, value);
            }
        }

        private Boolean m_removeable;
        public Boolean removeable
        {
            get { return m_removeable; }
            set
            {
                if (m_removeable == value) return;
                var OrgValue = m_removeable;
                m_removeable = value;
                OnPropertyChanged("removeable", OrgValue, value);
            }
        }

        private deadableType m_deadable;
        public deadableType deadable
        {
            get { return m_deadable; }
            set
            {
                if (m_deadable == value) return;
                var OrgValue = m_deadable;
                m_deadable = value;
                OnPropertyChanged("deadable", OrgValue, value);
            }
        }

        private String m_onlyFor;
        public String onlyFor
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

        private String m_description2;
        public String description2
        {
            get { return m_description2; }
            set
            {
                if (m_description2 == value) return;
                var OrgValue = m_description2;
                m_description2 = value;
                OnPropertyChanged("description2", OrgValue, value);
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

        private Double m_cost;
        public Double cost
        {
            get { return m_cost; }
            set
            {
                if (m_cost == value) return;
                var OrgValue = m_cost;
                m_cost = value;
                OnPropertyChanged("cost", OrgValue, value);
            }
        }

        private Boolean m_trigger;
        public Boolean trigger
        {
            get { return m_trigger; }
            set
            {
                if (m_trigger == value) return;
                var OrgValue = m_trigger;
                m_trigger = value;
                OnPropertyChanged("trigger", OrgValue, value);
            }
        }

        private Double m_success;
        public Double success
        {
            get { return m_success; }
            set
            {
                if (m_success == value) return;
                var OrgValue = m_success;
                m_success = value;
                OnPropertyChanged("success", OrgValue, value);
            }
        }

        public void Save()
        {
            if (Editored || EquipmentProp != null && EquipmentProp.Editor)
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
                m_Item.name = this.name;
                m_Item.description = this.description;
                m_Item.id = this.id;
                m_Item.filePath = this.filePath;
                m_Item.throwable = this.throwable;
                m_Item.disable = this.disable;
                m_Item.packable = this.packable;
                m_Item.buy = this.buy;
                m_Item.sell = this.sell;
                m_Item.effect = this.effect;
                m_Item.animation = this.animation;
                m_Item.forward = this.forward.ToString();
                m_Item.range = this.range.ToString();
                m_Item.removeable = this.removeable;
                m_Item.deadable = this.deadable.ToString();
                m_Item.onlyFor = this.onlyFor;
                m_Item.description2 = this.description2;
                m_Item.cost = this.cost;
                m_Item.trigger = this.trigger;
                m_Item.success = this.success;
                m_Item.id = this.id;
                m_Item.filePath = this.filePath;
                m_Item.description = this.description;
                m_Item.name = this.name;
                m_Item.type = this.type.ToString();
                m_Item.throwable = this.throwable;
                m_Item.disable = this.disable;
                //m_Item.use = this.use;
                if (this.type == ItemType.Equipment)
                {
                //m_Item.illustration2 = this.illustration2;
                //m_Item.onlyFor = this.onlyFor;
                //m_Item.equipType = this.equipType.ToString();
                //m_Item.equipProp.SetValue( this.EquipmentProp);
                m_Item.effect.prop = this.EquipmentProp;
                m_Item.equipType = this.equipType.ToString();
                    m_Item.effect.buffs = this.Buffs.Select(item => item.Clone()).ToList();
                }
                else if(this.type == ItemType.Spellcard)
                {
                    m_Item.effect.prop = this.EquipmentProp;
                    m_Item.effect.buffs = this.Buffs.Select(item => item.Clone()).ToList();
                }
            }
        }

        protected override void OnPropertyChanged(string PropName, object Oldvalue, object NewValue)
        {
           
            base.OnPropertyChanged(PropName, Oldvalue, NewValue);
        }
    }
}
