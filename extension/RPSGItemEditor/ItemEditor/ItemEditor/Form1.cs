using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using Newtonsoft.Json.Linq;

namespace ItemEditor
{
    public partial class Form1 : Form
    {
        String rootpath,path;
        List<Item> items = new List<Item>();
        public Form1()
        {
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            comboBox1.SelectedIndex = 0;
        }

        private void button6_Click(object sender, EventArgs e)
        {

        }

        private void 打开Assets目录ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            FolderBrowserDialog folderBrowserDialog = new FolderBrowserDialog();
            folderBrowserDialog.SelectedPath = "e:\\Workspaces\\MyEclipse 8.5\\rpg\\android\\assets";

            if (folderBrowserDialog.ShowDialog() != DialogResult.OK)
                return;
            rootpath = folderBrowserDialog.SelectedPath;
            path = rootpath + "/script/data";
            generateList();
            
        }

        private void generateList() {
            SearchBox.Clear();
            ItemList.Items.Clear();
            if (!checkExist())
                return;
            //each
            String[] files = Directory.GetFiles(@path);
            foreach(String file in files){
                Item item = new Item();
                item.filePath = file;
                StreamReader reader = File.OpenText(file);
                item.id = int.Parse(Path.GetFileNameWithoutExtension(file));

                JObject json = JObject.Parse(reader.ReadToEnd());
                item.realName = json.GetValue("name").ToString();
                item.name = item.id + " - " + item.realName;
                item.type = json.GetValue("type").ToString();
                if (json.GetValue("disable")!=null)
                    item.disable = bool.Parse(json.GetValue("disable").ToString());
                else
                    item.disable = false;
                item.use = json.GetValue("use").ToString();
                item.illustration = json.GetValue("illustration").ToString();

                items.Add(item);
                ItemList.Items.Add(item);
            }
            
        }

       

        private bool checkExist(){
            bool result = Directory.Exists(@path);
            if (!result)
                MessageBox.Show("请先选择工程目录，或工程目录不正确。");
            return result;
        }

        private void button5_Click(object sender, EventArgs e)
        {
            Item item = ((Item)ItemList.SelectedItem);
            if (item == null)
                return;

            if (File.Exists(rootpath + "/images/icons/i" + item.id + ".png"))
                IconBox.Image = Image.FromFile(rootpath + "/images/icons/i" + item.id + ".png");
            else
                IconBox.Image = Image.FromFile(rootpath + "/images/icons/i0.png");

            ThrowableBox.Checked = item.throwable;
            EnableBox.Checked = !item.disable;
        
            IDBox.Text = item.id+"";
            NameBox.Text = item.realName;
            IllustrationBox.Text = item.illustration;
            if (item.type.Equals("equipment", StringComparison.OrdinalIgnoreCase)) 
                TypeEquipmentBox.Checked = true;

            if (item.type.Equals("item", StringComparison.OrdinalIgnoreCase))
                TypeItemBox.Checked = true;
            

        }

        private void ThrowableBox_CheckedChanged(object sender, EventArgs e)
        {
            Item item = ((Item)ItemList.SelectedItem);
            MessageBox.Show(JObject.FromObject(item).ToString());
        }

        private void menuStrip1_ItemClicked(object sender, ToolStripItemClickedEventArgs e)
        {
            
        }

    }
}
