using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace ItemEditor
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        public MainWindow()
        {
            InitializeComponent();

//            Type type = typeof(ItemBase);
//            List<string> list = new List<string>();
//            foreach (var item in type.GetProperties())
//            {
//                list.Add(string.Format(@"
//        private {2} m_{1};
//        public {2} {1}
//        {3}
//            get {3} return m_{1}; {4}
//            set
//            {3}
//                if (m_{1} == value) return;
//                var OrgValue = m_{1};
//                m_{1} = value;
//                OnPropertyChanged({0}{1}{0}, OrgValue, value);
//            {4}
//        {4}", '"', item.Name, item.PropertyType.Name, '{', '}'));
//            }
//            System.IO.File.WriteAllLines("J:\\1.txt",list.ToArray());
        }

        private void CommandBinding_Save(object sender, ExecutedRoutedEventArgs e)
        {
            if (!IsLoaded) return;
            if (m_mainVM.Items.Select(x => x.id).Distinct().Count() != m_mainVM.Items.Count)
            {
                System.Windows.Forms.MessageBox.Show("存在重复id");
                return;
            }
            foreach (ItemBaseViewModel item in m_mainVM.Items)
            {
                item.Save();
            }
            foreach (var item in m_mainVM.Buffs)
            {
                item.Save();
            }
            foreach (ItemBaseViewModel item in m_mainVM.Deleteitems)
            {
                if (m_mainVM.Items.Any(x => x.id == item.id))
                {
                    continue;
                }
                item.Item.Remove(m_mainVM.RootPath);
            }

            foreach (var item in m_mainVM.DeleteBuffs)
            {
                if (m_mainVM.Buffs.Any(x => x.id == item.id))
                {
                    continue;
                }
                item.Remove(m_mainVM.RootPath);
            }
        }

        private void CommandBinding_Open(object sender, ExecutedRoutedEventArgs e)
        {
            if (!IsLoaded) return;
            using (System.Windows.Forms.FolderBrowserDialog dlg = new System.Windows.Forms.FolderBrowserDialog())
            {
                dlg.SelectedPath = ItemEditor.Properties.Settings.Default.RootPath;
                if (dlg.ShowDialog() == System.Windows.Forms.DialogResult.OK)
                {
                    ItemEditor.Properties.Settings.Default.RootPath = dlg.SelectedPath;
                    m_mainVM.RootPath = dlg.SelectedPath;
                }
            }
        }

        private void CommandBinding_Close(object sender, ExecutedRoutedEventArgs e)
        {
            if (!IsLoaded) return;
            this.Close();
        }

        private MainWindowViewModel m_mainVM = null;
        private void Window_Loaded(object sender, RoutedEventArgs e)
        {
            m_mainVM = (MainWindowViewModel)DataContext;
        }

        protected override void OnClosed(EventArgs e)
        {
            ItemEditor.Properties.Settings.Default.Save();
            base.OnClosed(e);
        }

        private void Button_Click(object sender, RoutedEventArgs e)
        {
            using (System.Windows.Forms.OpenFileDialog dlg = new System.Windows.Forms.OpenFileDialog())
            {
                dlg.FileName = ItemEditor.Properties.Settings.Default.ImagePath;
                dlg.Filter = "*.png|*.png";
                if (dlg.ShowDialog() == System.Windows.Forms.DialogResult.OK)
                {
                    ItemEditor.Properties.Settings.Default.ImagePath = dlg.FileName;
                    m_mainVM.CurrentItem.TempIconPath = dlg.FileName;
                }
            }
        }

        private void Button_Click_1(object sender, RoutedEventArgs e)
        {
            ItemBase item = new ItemBase();
            item.id = m_mainVM.Items.Max(x => x.id) + 1;
            ItemBaseViewModel vm = new ItemBaseViewModel();
            vm.Item = item;
            m_mainVM.Items.Add(vm);
        }

        private void Button_Click_2(object sender, RoutedEventArgs e)
        {
            var lst = lstItems.SelectedItems.Cast<ItemBaseViewModel>().ToList();
            foreach (ItemBaseViewModel item in lst)
	        {
		        m_mainVM.Items.Remove(item);
	        }
        }

        private void Button_Click_3(object sender, RoutedEventArgs e)
        {
            //TODO:Effect
        }

        private void BUFFADD(object sender, RoutedEventArgs e)
        {
            Buff item = new Buff();
            item.id = m_mainVM.Buffs.Max(x => x.id) + 1;
            m_mainVM.Buffs.Add(item);
        }
    }
}
