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
                    ItemBase item = new ItemBase();
                    try
                    {
                        item.Read(file);
                        ItemBaseViewModel itemvm = new ItemBaseViewModel();
                        itemvm.Item = item;
                        m_Items.Add(itemvm);
                    }
                    catch (Exception)
                    {
                        
                    }

                }
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
