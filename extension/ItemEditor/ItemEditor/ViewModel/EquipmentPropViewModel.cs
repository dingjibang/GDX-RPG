using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ItemEditor
{
    public class EquipmentPropViewModel:ViewModelBase
    {
        private Double m_maxhp;

        public Double maxhp
        {
            get { return m_maxhp; }
            set
            {
                if (m_maxhp == value) return;
                var OrgValue = m_maxhp;
                m_maxhp = value;
                OnPropertyChanged("maxhp", OrgValue, value);
            }
        }

        private Double m_mapmp;

        public Double mapmp
        {
            get { return m_mapmp; }
            set
            {
                if (m_mapmp == value) return;
                var OrgValue = m_mapmp;
                m_mapmp = value;
                OnPropertyChanged("mapmp", OrgValue, value);
            }
        }

        private Double m_attack;

        public Double attack
        {
            get { return m_attack; }
            set
            {
                if (m_attack == value) return;
                var OrgValue = m_attack;
                m_attack = value;
                OnPropertyChanged("attack", OrgValue, value);
            }
        }

        private Double m_magicAttack;

        public Double magicAttack
        {
            get { return m_magicAttack; }
            set
            {
                if (m_magicAttack == value) return;
                var OrgValue = m_magicAttack;
                m_magicAttack = value;
                OnPropertyChanged("magicAttack", OrgValue, value);
            }
        }

        private Double m_defense;

        public Double defense
        {
            get { return m_defense; }
            set
            {
                if (m_defense == value) return;
                var OrgValue = m_defense;
                m_defense = value;
                OnPropertyChanged("defense", OrgValue, value);
            }
        }


        private double m_magicDefense;

        public double magicDefense
        {
            get { return m_magicDefense; }
            set
            {
                if (m_magicDefense == value) return;
                var OrgValue = m_magicDefense;
                m_magicDefense = value;
                OnPropertyChanged("magicDefense", OrgValue, value);
            }
        }


        private double m_speed;

        public double speed
        {
            get { return m_speed; }
            set
            {
                if (m_speed == value) return;
                var OrgValue = m_speed;
                m_speed = value;
                OnPropertyChanged("speed", OrgValue, value);
            }
        }

        private double m_hit;

        public double hit
        {
            get { return m_hit; }
            set
            {
                if (m_hit == value) return;
                var OrgValue = m_hit;
                m_hit = value;
                OnPropertyChanged("hit", OrgValue, value);
            }
        }
        
    }
}
