namespace ItemEditor
{
    partial class Form1
    {
        /// <summary>
        /// 必需的设计器变量。
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// 清理所有正在使用的资源。
        /// </summary>
        /// <param name="disposing">如果应释放托管资源，为 true；否则为 false。</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows 窗体设计器生成的代码

        /// <summary>
        /// 设计器支持所需的方法 - 不要修改
        /// 使用代码编辑器修改此方法的内容。
        /// </summary>
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            this.menuStrip1 = new System.Windows.Forms.MenuStrip();
            this.文件ToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.打开Assets目录ToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.ItemList = new System.Windows.Forms.ListBox();
            this.IconBox = new System.Windows.Forms.PictureBox();
            this.label1 = new System.Windows.Forms.Label();
            this.NameBox = new System.Windows.Forms.TextBox();
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.TypeTaskBox = new System.Windows.Forms.RadioButton();
            this.TypeCookingBox = new System.Windows.Forms.RadioButton();
            this.TypeSpellcardBox = new System.Windows.Forms.RadioButton();
            this.TypeItemBox = new System.Windows.Forms.RadioButton();
            this.TypeEquipmentBox = new System.Windows.Forms.RadioButton();
            this.IDBox = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.button1 = new System.Windows.Forms.Button();
            this.button2 = new System.Windows.Forms.Button();
            this.EnableBox = new System.Windows.Forms.CheckBox();
            this.ThrowableBox = new System.Windows.Forms.CheckBox();
            this.label3 = new System.Windows.Forms.Label();
            this.IllustrationBox = new System.Windows.Forms.TextBox();
            this.button3 = new System.Windows.Forms.Button();
            this.button4 = new System.Windows.Forms.Button();
            this.readItemButton = new System.Windows.Forms.Button();
            this.button6 = new System.Windows.Forms.Button();
            this.comboBox1 = new System.Windows.Forms.ComboBox();
            this.button7 = new System.Windows.Forms.Button();
            this.SearchBox = new System.Windows.Forms.TextBox();
            this.toolTip1 = new System.Windows.Forms.ToolTip(this.components);
            this.refreshButton = new System.Windows.Forms.Button();
            this.button9 = new System.Windows.Forms.Button();
            this.button10 = new System.Windows.Forms.Button();
            this.menuStrip1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.IconBox)).BeginInit();
            this.groupBox1.SuspendLayout();
            this.SuspendLayout();
            // 
            // menuStrip1
            // 
            this.menuStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.文件ToolStripMenuItem});
            this.menuStrip1.Location = new System.Drawing.Point(0, 0);
            this.menuStrip1.Name = "menuStrip1";
            this.menuStrip1.Padding = new System.Windows.Forms.Padding(9, 3, 0, 3);
            this.menuStrip1.Size = new System.Drawing.Size(1022, 27);
            this.menuStrip1.TabIndex = 0;
            this.menuStrip1.Text = "menuStrip1";
            this.menuStrip1.ItemClicked += new System.Windows.Forms.ToolStripItemClickedEventHandler(this.menuStrip1_ItemClicked);
            // 
            // 文件ToolStripMenuItem
            // 
            this.文件ToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.打开Assets目录ToolStripMenuItem});
            this.文件ToolStripMenuItem.Name = "文件ToolStripMenuItem";
            this.文件ToolStripMenuItem.Size = new System.Drawing.Size(44, 21);
            this.文件ToolStripMenuItem.Text = "文件";
            // 
            // 打开Assets目录ToolStripMenuItem
            // 
            this.打开Assets目录ToolStripMenuItem.Name = "打开Assets目录ToolStripMenuItem";
            this.打开Assets目录ToolStripMenuItem.Size = new System.Drawing.Size(161, 22);
            this.打开Assets目录ToolStripMenuItem.Text = "打开Assets目录";
            this.打开Assets目录ToolStripMenuItem.Click += new System.EventHandler(this.打开Assets目录ToolStripMenuItem_Click);
            // 
            // ItemList
            // 
            this.ItemList.Font = new System.Drawing.Font("微软雅黑", 11F);
            this.ItemList.FormattingEnabled = true;
            this.ItemList.ItemHeight = 20;
            this.ItemList.Location = new System.Drawing.Point(18, 131);
            this.ItemList.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.ItemList.Name = "ItemList";
            this.ItemList.Size = new System.Drawing.Size(324, 384);
            this.ItemList.TabIndex = 1;
            // 
            // IconBox
            // 
            this.IconBox.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.IconBox.Location = new System.Drawing.Point(607, 49);
            this.IconBox.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.IconBox.Name = "IconBox";
            this.IconBox.Size = new System.Drawing.Size(200, 200);
            this.IconBox.TabIndex = 2;
            this.IconBox.TabStop = false;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(614, 307);
            this.label1.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(84, 20);
            this.label1.TabIndex = 3;
            this.label1.Text = "物品名称：";
            // 
            // NameBox
            // 
            this.NameBox.Location = new System.Drawing.Point(706, 305);
            this.NameBox.Name = "NameBox";
            this.NameBox.Size = new System.Drawing.Size(295, 27);
            this.NameBox.TabIndex = 4;
            // 
            // groupBox1
            // 
            this.groupBox1.Controls.Add(this.TypeTaskBox);
            this.groupBox1.Controls.Add(this.TypeCookingBox);
            this.groupBox1.Controls.Add(this.TypeSpellcardBox);
            this.groupBox1.Controls.Add(this.TypeItemBox);
            this.groupBox1.Controls.Add(this.TypeEquipmentBox);
            this.groupBox1.Location = new System.Drawing.Point(618, 455);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Size = new System.Drawing.Size(383, 60);
            this.groupBox1.TabIndex = 5;
            this.groupBox1.TabStop = false;
            this.groupBox1.Text = "物品类型：";
            // 
            // TypeTaskBox
            // 
            this.TypeTaskBox.AutoSize = true;
            this.TypeTaskBox.Location = new System.Drawing.Point(307, 26);
            this.TypeTaskBox.Name = "TypeTaskBox";
            this.TypeTaskBox.Size = new System.Drawing.Size(57, 24);
            this.TypeTaskBox.TabIndex = 4;
            this.TypeTaskBox.TabStop = true;
            this.TypeTaskBox.Text = "任务";
            this.TypeTaskBox.UseVisualStyleBackColor = true;
            // 
            // TypeCookingBox
            // 
            this.TypeCookingBox.AutoSize = true;
            this.TypeCookingBox.Location = new System.Drawing.Point(236, 26);
            this.TypeCookingBox.Name = "TypeCookingBox";
            this.TypeCookingBox.Size = new System.Drawing.Size(57, 24);
            this.TypeCookingBox.TabIndex = 3;
            this.TypeCookingBox.TabStop = true;
            this.TypeCookingBox.Text = "料理";
            this.TypeCookingBox.UseVisualStyleBackColor = true;
            // 
            // TypeSpellcardBox
            // 
            this.TypeSpellcardBox.AutoSize = true;
            this.TypeSpellcardBox.Location = new System.Drawing.Point(165, 26);
            this.TypeSpellcardBox.Name = "TypeSpellcardBox";
            this.TypeSpellcardBox.Size = new System.Drawing.Size(57, 24);
            this.TypeSpellcardBox.TabIndex = 2;
            this.TypeSpellcardBox.TabStop = true;
            this.TypeSpellcardBox.Text = "符卡";
            this.TypeSpellcardBox.UseVisualStyleBackColor = true;
            // 
            // TypeItemBox
            // 
            this.TypeItemBox.AutoSize = true;
            this.TypeItemBox.Location = new System.Drawing.Point(94, 26);
            this.TypeItemBox.Name = "TypeItemBox";
            this.TypeItemBox.Size = new System.Drawing.Size(57, 24);
            this.TypeItemBox.TabIndex = 1;
            this.TypeItemBox.TabStop = true;
            this.TypeItemBox.Text = "道具";
            this.TypeItemBox.UseVisualStyleBackColor = true;
            // 
            // TypeEquipmentBox
            // 
            this.TypeEquipmentBox.AutoSize = true;
            this.TypeEquipmentBox.Location = new System.Drawing.Point(23, 26);
            this.TypeEquipmentBox.Name = "TypeEquipmentBox";
            this.TypeEquipmentBox.Size = new System.Drawing.Size(57, 24);
            this.TypeEquipmentBox.TabIndex = 0;
            this.TypeEquipmentBox.TabStop = true;
            this.TypeEquipmentBox.Text = "装备";
            this.TypeEquipmentBox.UseVisualStyleBackColor = true;
            // 
            // IDBox
            // 
            this.IDBox.Location = new System.Drawing.Point(706, 263);
            this.IDBox.Name = "IDBox";
            this.IDBox.Size = new System.Drawing.Size(101, 27);
            this.IDBox.TabIndex = 9;
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(614, 265);
            this.label2.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(84, 20);
            this.label2.TabIndex = 8;
            this.label2.Text = "物品序号：";
            // 
            // button1
            // 
            this.button1.Location = new System.Drawing.Point(813, 263);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(91, 27);
            this.button1.TabIndex = 10;
            this.button1.Text = "自动生成";
            this.button1.UseVisualStyleBackColor = true;
            // 
            // button2
            // 
            this.button2.Location = new System.Drawing.Point(910, 263);
            this.button2.Name = "button2";
            this.button2.Size = new System.Drawing.Size(91, 27);
            this.button2.TabIndex = 11;
            this.button2.Text = "检查冲突";
            this.button2.UseVisualStyleBackColor = true;
            // 
            // EnableBox
            // 
            this.EnableBox.AutoSize = true;
            this.EnableBox.Location = new System.Drawing.Point(829, 93);
            this.EnableBox.Name = "EnableBox";
            this.EnableBox.Size = new System.Drawing.Size(73, 24);
            this.EnableBox.TabIndex = 9;
            this.EnableBox.Text = "已启用";
            this.EnableBox.UseVisualStyleBackColor = true;
            // 
            // ThrowableBox
            // 
            this.ThrowableBox.AutoSize = true;
            this.ThrowableBox.Location = new System.Drawing.Point(829, 63);
            this.ThrowableBox.Name = "ThrowableBox";
            this.ThrowableBox.Size = new System.Drawing.Size(103, 24);
            this.ThrowableBox.TabIndex = 8;
            this.ThrowableBox.Text = "无法丢弃的";
            this.ThrowableBox.UseVisualStyleBackColor = true;
            this.ThrowableBox.CheckedChanged += new System.EventHandler(this.ThrowableBox_CheckedChanged);
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(614, 379);
            this.label3.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(84, 20);
            this.label3.TabIndex = 12;
            this.label3.Text = "物品描述：";
            // 
            // IllustrationBox
            // 
            this.IllustrationBox.Location = new System.Drawing.Point(706, 349);
            this.IllustrationBox.Multiline = true;
            this.IllustrationBox.Name = "IllustrationBox";
            this.IllustrationBox.Size = new System.Drawing.Size(295, 88);
            this.IllustrationBox.TabIndex = 13;
            // 
            // button3
            // 
            this.button3.ForeColor = System.Drawing.Color.Red;
            this.button3.Location = new System.Drawing.Point(363, 51);
            this.button3.Name = "button3";
            this.button3.Size = new System.Drawing.Size(219, 54);
            this.button3.TabIndex = 14;
            this.button3.Text = "<- 保存";
            this.button3.UseVisualStyleBackColor = true;
            // 
            // button4
            // 
            this.button4.Location = new System.Drawing.Point(363, 349);
            this.button4.Name = "button4";
            this.button4.Size = new System.Drawing.Size(219, 59);
            this.button4.TabIndex = 15;
            this.button4.Text = "还原";
            this.button4.UseVisualStyleBackColor = true;
            // 
            // readItemButton
            // 
            this.readItemButton.Location = new System.Drawing.Point(363, 167);
            this.readItemButton.Name = "readItemButton";
            this.readItemButton.Size = new System.Drawing.Size(219, 55);
            this.readItemButton.TabIndex = 16;
            this.readItemButton.Text = "读取->";
            this.readItemButton.UseVisualStyleBackColor = true;
            this.readItemButton.Click += new System.EventHandler(this.button5_Click);
            // 
            // button6
            // 
            this.button6.Location = new System.Drawing.Point(363, 236);
            this.button6.Name = "button6";
            this.button6.Size = new System.Drawing.Size(219, 53);
            this.button6.TabIndex = 17;
            this.button6.Text = "新建";
            this.button6.UseVisualStyleBackColor = true;
            this.button6.Click += new System.EventHandler(this.button6_Click);
            // 
            // comboBox1
            // 
            this.comboBox1.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.comboBox1.FormattingEnabled = true;
            this.comboBox1.Items.AddRange(new object[] {
            "显示所有道具"});
            this.comboBox1.Location = new System.Drawing.Point(18, 51);
            this.comboBox1.Name = "comboBox1";
            this.comboBox1.Size = new System.Drawing.Size(324, 28);
            this.comboBox1.TabIndex = 19;
            // 
            // button7
            // 
            this.button7.Location = new System.Drawing.Point(829, 207);
            this.button7.Name = "button7";
            this.button7.Size = new System.Drawing.Size(172, 33);
            this.button7.TabIndex = 20;
            this.button7.Text = "编辑道具源代码";
            this.button7.UseVisualStyleBackColor = true;
            // 
            // SearchBox
            // 
            this.SearchBox.AcceptsTab = true;
            this.SearchBox.Font = new System.Drawing.Font("Microsoft Sans Serif", 11.25F, System.Drawing.FontStyle.Italic, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.SearchBox.ForeColor = System.Drawing.Color.DarkGray;
            this.SearchBox.Location = new System.Drawing.Point(18, 93);
            this.SearchBox.Name = "SearchBox";
            this.SearchBox.Size = new System.Drawing.Size(324, 24);
            this.SearchBox.TabIndex = 21;
            this.SearchBox.Text = "搜索道具";
            // 
            // refreshButton
            // 
            this.refreshButton.Location = new System.Drawing.Point(363, 456);
            this.refreshButton.Name = "refreshButton";
            this.refreshButton.Size = new System.Drawing.Size(219, 59);
            this.refreshButton.TabIndex = 22;
            this.refreshButton.Text = "重新读取所有道具";
            this.refreshButton.UseVisualStyleBackColor = true;
            // 
            // button9
            // 
            this.button9.Location = new System.Drawing.Point(829, 129);
            this.button9.Name = "button9";
            this.button9.Size = new System.Drawing.Size(172, 33);
            this.button9.TabIndex = 23;
            this.button9.Text = "选择物品图标";
            this.button9.UseVisualStyleBackColor = true;
            // 
            // button10
            // 
            this.button10.Location = new System.Drawing.Point(829, 168);
            this.button10.Name = "button10";
            this.button10.Size = new System.Drawing.Size(172, 33);
            this.button10.TabIndex = 24;
            this.button10.Text = "删除物品图标";
            this.button10.UseVisualStyleBackColor = true;
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(9F, 20F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1022, 544);
            this.Controls.Add(this.button10);
            this.Controls.Add(this.button9);
            this.Controls.Add(this.refreshButton);
            this.Controls.Add(this.SearchBox);
            this.Controls.Add(this.button7);
            this.Controls.Add(this.comboBox1);
            this.Controls.Add(this.button6);
            this.Controls.Add(this.readItemButton);
            this.Controls.Add(this.button4);
            this.Controls.Add(this.button3);
            this.Controls.Add(this.EnableBox);
            this.Controls.Add(this.IllustrationBox);
            this.Controls.Add(this.ThrowableBox);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.button2);
            this.Controls.Add(this.button1);
            this.Controls.Add(this.IDBox);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.groupBox1);
            this.Controls.Add(this.NameBox);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.IconBox);
            this.Controls.Add(this.ItemList);
            this.Controls.Add(this.menuStrip1);
            this.Font = new System.Drawing.Font("微软雅黑", 11F);
            this.MainMenuStrip = this.menuStrip1;
            this.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.MaximizeBox = false;
            this.Name = "Form1";
            this.SizeGripStyle = System.Windows.Forms.SizeGripStyle.Hide;
            this.Text = "秘封异闻录 - 物品编辑器";
            this.Load += new System.EventHandler(this.Form1_Load);
            this.menuStrip1.ResumeLayout(false);
            this.menuStrip1.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.IconBox)).EndInit();
            this.groupBox1.ResumeLayout(false);
            this.groupBox1.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.MenuStrip menuStrip1;
        private System.Windows.Forms.ToolStripMenuItem 文件ToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem 打开Assets目录ToolStripMenuItem;
        private System.Windows.Forms.ListBox ItemList;
        private System.Windows.Forms.PictureBox IconBox;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.TextBox NameBox;
        private System.Windows.Forms.GroupBox groupBox1;
        private System.Windows.Forms.RadioButton TypeTaskBox;
        private System.Windows.Forms.RadioButton TypeCookingBox;
        private System.Windows.Forms.RadioButton TypeSpellcardBox;
        private System.Windows.Forms.RadioButton TypeItemBox;
        private System.Windows.Forms.RadioButton TypeEquipmentBox;
        private System.Windows.Forms.TextBox IDBox;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Button button1;
        private System.Windows.Forms.Button button2;
        private System.Windows.Forms.CheckBox EnableBox;
        private System.Windows.Forms.CheckBox ThrowableBox;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.TextBox IllustrationBox;
        private System.Windows.Forms.Button button3;
        private System.Windows.Forms.Button button4;
        private System.Windows.Forms.Button readItemButton;
        private System.Windows.Forms.Button button6;
        private System.Windows.Forms.ComboBox comboBox1;
        private System.Windows.Forms.Button button7;
        private System.Windows.Forms.TextBox SearchBox;
        private System.Windows.Forms.ToolTip toolTip1;
        private System.Windows.Forms.Button refreshButton;
        private System.Windows.Forms.Button button9;
        private System.Windows.Forms.Button button10;
    }
}

