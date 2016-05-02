using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ItemEditor
{
    public class MainWindowViewModel:ViewModelBase
    {
        public MainWindowViewModel()
        {
            m_Items.CollectionChanged += m_Items_CollectionChanged;
            m_Buffs.CollectionChanged += m_Buffs_CollectionChanged;
        }

        void m_Items_CollectionChanged(object sender, System.Collections.Specialized.NotifyCollectionChangedEventArgs e)
        {
            switch (e.Action)
            {
                case System.Collections.Specialized.NotifyCollectionChangedAction.Add:
                    {
                        foreach (ItemBaseViewModel item in e.NewItems)
                        {
                            item.RootPath = RootPath;
                        }
                    }
                    break;
                case System.Collections.Specialized.NotifyCollectionChangedAction.Move:
                    break;
                case System.Collections.Specialized.NotifyCollectionChangedAction.Remove:
                    {
                        foreach (ItemBaseViewModel item in e.OldItems)
                        {
                            this.m_deleteitems.Add(item);
                        }
                    }
                    break;
                case System.Collections.Specialized.NotifyCollectionChangedAction.Replace:
                    break;
                case System.Collections.Specialized.NotifyCollectionChangedAction.Reset:
                    break;
                default:
                    break;
            }
        }


        void m_Buffs_CollectionChanged(object sender, System.Collections.Specialized.NotifyCollectionChangedEventArgs e)
        {
            switch (e.Action)
            {
                case System.Collections.Specialized.NotifyCollectionChangedAction.Add:
                    {
                        foreach (Buff item in e.NewItems)
                        {
                            item.RootPath = RootPath;
                        }
                    }
                    break;
                case System.Collections.Specialized.NotifyCollectionChangedAction.Move:
                    break;
                case System.Collections.Specialized.NotifyCollectionChangedAction.Remove:
                    {
                        foreach (Buff item in e.OldItems)
                        {
                            this.m_deleteBuffs.Add(item);
                        }
                    }
                    break;
                case System.Collections.Specialized.NotifyCollectionChangedAction.Replace:
                    break;
                case System.Collections.Specialized.NotifyCollectionChangedAction.Reset:
                    break;
                default:
                    break;
            }
        }

        private List<Buff> m_deleteBuffs = new List<Buff>();

        public List<Buff> DeleteBuffs
        {
            get { return m_deleteBuffs; }
            set { m_deleteBuffs = value; }
        }

        private List<ItemBaseViewModel> m_deleteitems = new List<ItemBaseViewModel>();

        public List<ItemBaseViewModel> Deleteitems
        {
            get { return m_deleteitems; }
            set { m_deleteitems = value; }
        }

        private ObservableCollection<ItemBaseViewModel> m_Items = new ObservableCollection<ItemBaseViewModel>();

        public ObservableCollection<ItemBaseViewModel> Items
        {
            get { return m_Items; }
            set
            {
                if (m_Items == value) return;
                var OrgValue = m_Items;
                m_Items = value;
                OnPropertyChanged("Items", OrgValue, value);
            }
        }

        private ObservableCollection<Buff> m_Buffs = new ObservableCollection<Buff>();

        public ObservableCollection<Buff> Buffs
        {
            get { return m_Buffs; }
            set
            {
                if (m_Buffs == value) return;
                var OrgValue = m_Items;
                m_Buffs = value;
                OnPropertyChanged("Buffs", OrgValue, value);
            }
        }


        private ItemBaseViewModel m_CurrentItem;

        public ItemBaseViewModel CurrentItem
        {
            get { return m_CurrentItem; }
            set
            {
                if (m_CurrentItem == value) return;
                var OrgValue = m_CurrentItem;
                m_CurrentItem = value;
                OnPropertyChanged("CurrentItem", OrgValue, value);
            }
        }


        private Buff m_CurrentBuff;

        public Buff CurrentBuff
        {
            get { return m_CurrentBuff; }
            set
            {
                if (m_CurrentBuff == value) return;
                var OrgValue = m_CurrentBuff;
                m_CurrentBuff = value;
                OnPropertyChanged("CurrentBuff", OrgValue, value);
            }
        }

        private string m_RootPath;

        public string RootPath
        {
            get { return m_RootPath; }
            set
            {
                //if (m_RootPath == value) return;
                var OrgValue = m_RootPath;
                m_RootPath = value;
                OnPropertyChanged("RootPath", OrgValue, value);

                CurrentItem = null;
                m_Items.Clear();
                m_deleteitems.Clear();
                m_Buffs.Clear();
                m_deleteBuffs.Clear();

                if (!System.IO.Directory.Exists(DataPath))
                {
                    return;
                }
                String[] files = System.IO.Directory.GetFiles(DataPath);
                foreach (String file in files)
                {
                    ItemBase item = new ItemBase();
                    try
                    {
                        item.Read(file);
                        ItemBaseViewModel itemvm = new ItemBaseViewModel();
                        itemvm.Item = item;
                        m_Items.Add(itemvm);
                    }
                    catch (Exception ex)
                    {
                        
                    }

                }

                files = System.IO.Directory.GetFiles(BUFFSPath);
                foreach (String file in files)
                {
                    
                    try
                    {
                        Buff item = new Buff(file);
                        m_Buffs.Add(item);
                    }
                    catch (Exception ex)
                    {

                    }

                }
            }
        }


        public string BUFFSPath
        {
            get
            {
                return RootPath + "\\script\\data\\buff";
            }
        }

        public string DataPath
        {
            get {
                return RootPath + "\\script\\data\\item";
            }
        }
    }
}
