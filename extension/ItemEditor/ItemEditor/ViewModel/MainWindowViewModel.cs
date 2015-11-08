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
        }

        void m_Items_CollectionChanged(object sender, System.Collections.Specialized.NotifyCollectionChangedEventArgs e)
        {
            switch (e.Action)
            {
                case System.Collections.Specialized.NotifyCollectionChangedAction.Add:
                    {
                        foreach (ItemViewModel item in e.NewItems)
                        {
                            item.RootPath = RootPath;
                        }
                    }
                    break;
                case System.Collections.Specialized.NotifyCollectionChangedAction.Move:
                    break;
                case System.Collections.Specialized.NotifyCollectionChangedAction.Remove:
                    {
                        foreach (ItemViewModel item in e.OldItems)
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

        private List<ItemViewModel> m_deleteitems = new List<ItemViewModel>();

        public List<ItemViewModel> Deleteitems
        {
            get { return m_deleteitems; }
            set { m_deleteitems = value; }
        }

        private ObservableCollection<ItemViewModel> m_Items = new ObservableCollection<ItemViewModel>();

        public ObservableCollection<ItemViewModel> Items
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

        private ItemViewModel m_CurrentItem;

        public ItemViewModel CurrentItem
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

                if (!System.IO.Directory.Exists(DataPath))
                {
                    return;
                }
                String[] files = System.IO.Directory.GetFiles(DataPath);
                foreach (String file in files)
                {
                    Item item = new Item();
                    item.Read(file);
                    ItemViewModel itemvm = new ItemViewModel();
                    itemvm.Item = item;
                    m_Items.Add(itemvm);
                }
            }
        }


        public string DataPath
        {
            get {
                return RootPath + "\\script\\data";
            }
        }
    }
}
