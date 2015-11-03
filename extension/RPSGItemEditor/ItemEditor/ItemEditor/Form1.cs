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

namespace ItemEditor
{
    public partial class Form1 : Form
    {
        String path;
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
            if (folderBrowserDialog.ShowDialog() == DialogResult.OK){
                path = folderBrowserDialog.SelectedPath;
                generateList();
            }
        }

        private void generateList() {
            SearchBox.Clear();
            ItemList.Items.Clear();
           
            
        }

        private bool checkExist(){
            String spath = path + "/script/data";
            bool result = File.Exists(spath);
            if (!result)
                MessageBox.Show("请先选择工程目录，或工程目录不正确。");
            return result;
        }

        private void menuStrip1_ItemClicked(object sender, ToolStripItemClickedEventArgs e)
        {

        }
    }
}
