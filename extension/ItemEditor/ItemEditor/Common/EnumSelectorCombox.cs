using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Diagnostics;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;

namespace ItemEditor
{
    public class EnumSelectorCombox : ComboBox
    {
        public class EnumItem
        {
            public Object Value { get; set; }
            public string Caption { get; set; }
        }
        static EnumSelectorCombox()
        {
            DefaultStyleKeyProperty.OverrideMetadata(typeof(EnumSelectorCombox), new FrameworkPropertyMetadata(typeof(EnumSelectorCombox)));
        }

        public EnumSelectorCombox()
            : base()
        {
            this.ItemsSource = m_ItemSource;
        }

        public object EditorSource
        {
            get { return (object)GetValue(EditorSourceProperty); }
            set { SetValue(EditorSourceProperty, value); }
        }

        private List<EnumItem> m_ItemSource = new List<EnumItem>();

        private bool m_IsInit = false;
        private Type m_curType = null;
        // Using a DependencyProperty as the backing store for EditorSource.  This enables animation, styling, binding, etc...
        public static readonly DependencyProperty EditorSourceProperty =
            DependencyProperty.Register("EditorSource", typeof(object), typeof(EnumSelectorCombox), new PropertyMetadata(null));

        protected override void OnPropertyChanged(DependencyPropertyChangedEventArgs e)
        {
            if (e.Property == EditorSourceProperty)
            {
                if (e.NewValue != e.OldValue)
                {
                    m_IsInit = true;
                    lock (m_ItemSource)
                    {
                        this.ItemsSource = null;

                        if (e.NewValue is Enum)
                        {
                            Type type = e.NewValue.GetType();
                            if (m_curType == null || type.FullName != m_curType.FullName)
                            {
                                m_ItemSource.Clear();
                                m_ItemSource.AddRange(
                                    Enum.GetNames(type).
                                    Select(
                                    s =>
                                    {
                                        Enum v = (Enum)Enum.Parse(type, s);
                                        string str = v.GetDisplayName();
                                        if (string.IsNullOrEmpty(str))
                                        {
                                            str = s;
                                        }
                                        return new EnumItem
                                        {
                                            Caption = str,
                                            Value = v
                                        };
                                    }
                                    ));
                            }

                            this.ItemsSource = m_ItemSource;
                            if (m_ItemSource.Count != 0) this.SelectedItem = m_ItemSource.First(i => Enum.Equals(i.Value, e.NewValue));
                            m_curType = type;
                        }
                        else
                        {
                            m_ItemSource.Clear();
                            m_curType = null;
                        }

                        m_IsInit = false;
                    }
                  
                }

            }
            base.OnPropertyChanged(e);
        }

        protected override void OnSelectionChanged(SelectionChangedEventArgs e)
        {
            if (!m_IsInit && EditorSource != null)
            {
                if (e.AddedItems.Count != 0 && m_curType != null)
                {
                    try
                    {
                        EditorSource = ((EnumItem)e.AddedItems[0]).Value;
                    }
                    catch
                    {
                        //Debug.Print(ex.Message);
                        EditorSource = null;
                    }
                }
                else
                {
                    EditorSource = null;
                }
            }
            base.OnSelectionChanged(e);
        }
    }

    public static class EnumHelper
    {
        public static T Set<T>(this Enum source, T flag, Boolean value)
        {
            if (!(source is T)) throw new ArgumentException("枚举标识判断必须是相同的类型!", "source");
            ulong s = Convert.ToUInt64(source);
            ulong f = Convert.ToUInt64(flag);

            if (value)
            {
                // 必须先检查是否包含这个标识位，因为异或的操作仅仅是取反
                if ((s & f) != f) s ^= f;
            }
            else
                s = s | f;

            return (T)Enum.ToObject(typeof(T), s);
        }

        public static Boolean Has(this Enum value, Enum flag)
        {
            if (value.GetType() != flag.GetType()) throw new ArgumentException("枚举标识判断必须是相同的类型！", "flag");
            ulong num = Convert.ToUInt64(flag);
            return (Convert.ToUInt64(value) & num) == num;
        }

        public static String GetDescription(this Enum value)
        {
            Type type = value.GetType();
            FieldInfo item = type.GetField(value.ToString(), BindingFlags.Public | BindingFlags.Static);
            if (item == null) return null;
            var attribute = Attribute.GetCustomAttribute(item, typeof(DescriptionAttribute)) as DescriptionAttribute;
            if (attribute != null && !String.IsNullOrEmpty(attribute.Description)) return attribute.Description;
            return null;
        }

        public static String GetDisplayName(this Enum value)
        {
            Type type = value.GetType();
            FieldInfo item = type.GetField(value.ToString(), BindingFlags.Public | BindingFlags.Static);
            if (item == null) return null;
            var attribute = Attribute.GetCustomAttribute(item, typeof(EnumDisplayNameAttribute)) as EnumDisplayNameAttribute;
            if (attribute != null && !String.IsNullOrEmpty(attribute.DisplayName)) return attribute.DisplayName;
            return null;
        }

        public static Dictionary<T, String> GetDescriptions<T>() where T : struct
        {
            return GetDescriptions<T>(typeof(T));
        }

        public static Dictionary<T, String> GetDescriptions<T>(Type type)
        {
            var dic = new Dictionary<T, String>();
            foreach (FieldInfo item in type.GetFields(BindingFlags.Public | BindingFlags.Static))
            {
                if (!item.IsStatic) continue;
                var value = (T)item.GetValue(null);
                string des = item.Name;
                var dna = Attribute.GetCustomAttribute(item, typeof(DisplayNameAttribute)) as DisplayNameAttribute;
                if (dna != null && !String.IsNullOrEmpty(dna.DisplayName)) des = dna.DisplayName;

                var att = Attribute.GetCustomAttribute(item, typeof(DescriptionAttribute)) as DescriptionAttribute;
                if (att != null && !String.IsNullOrEmpty(att.Description)) des = att.Description;
                dic.Add(value, des);
            }
            return dic;
        }
    }
}
