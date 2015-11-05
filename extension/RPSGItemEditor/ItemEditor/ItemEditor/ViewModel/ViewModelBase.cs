using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ItemEditor
{
    public class ViewModelBase : INotifyPropertyChanged
    {
        #region property

        private bool m_IsSelected;
        [Browsable(false)]
        [System.Xml.Serialization.XmlIgnore()]
        public bool IsSelected
        {
            get { return m_IsSelected; }
            set
            {
                if (m_IsSelected == value) return;
                object OrgValue = m_IsSelected;
                m_IsSelected = value;
                OnPropertyChanged("IsSelected", OrgValue, value);
            }
        }

        private System.Windows.Visibility m_Visible = System.Windows.Visibility.Visible;
        [Browsable(false)]
        [System.Xml.Serialization.XmlIgnore()]
        public System.Windows.Visibility Visible
        {
            get { return m_Visible; }
            set
            {
                if (m_Visible == value) return;
                object OrgValue = m_Visible;
                m_Visible = value;
                OnPropertyChanged("Visible", OrgValue, value);
            }

        }

        [Browsable(false)]
        public virtual string ElementName { get { return "ViewModelBase"; } }

        #endregion

        private bool m_isEditting;
        private Dictionary<string, PropertyRedoUndoCommand> m_EditCacheDic = new Dictionary<string, PropertyRedoUndoCommand>();

        public void BeginEdit()
        {
            m_isEditting = true;
        }
        public void EndEdit()
        {
            m_isEditting = false;
            if (m_EditCacheDic.Count != 0)
            {
                OnModified();
            }
        }

        protected virtual void OnPropertyChanged(string PropName, object Oldvalue, object NewValue)
        {
            if (Oldvalue == NewValue)
            {
                return;
            }

            if (PropName != "IsSelected" && PropName != "Visible")
            {
                if (!m_isUndoRedoing)
                {
                    //await Task.Run(() =>
                    //{
                    PropertyRedoUndoCommand cmm = null;

                    lock (m_EditCacheDic)
                    {
                        if (m_EditCacheDic.ContainsKey(PropName))
                        {
                            cmm = m_EditCacheDic[PropName];
                            cmm.NewValue = NewValue;
                        }
                        else
                        {
                            cmm = new PropertyRedoUndoCommand(this, PropName, Oldvalue, NewValue);
                            m_EditCacheDic.Add(PropName, cmm);
                        }
                    }

                    //});
                }
                if (!(m_isEditting || m_isUndoRedoing))
                {
                    OnModified();
                }
            }


            RaisePropertyChanged(PropName);
        }
        protected void RaisePropertyChanged(string PropName)
        {
            if (PropertyChanged != null)
            {
                PropertyChanged(this, new PropertyChangedEventArgs(PropName));
            }
        }
        protected virtual void OnModified()
        {
            if (m_isEditting || m_isUndoRedoing)
            {
                return;
            }
            lock (m_EditCacheDic)
            {
                var col = m_EditCacheDic.Values.ToList();
                m_EditCacheDic.Clear();
                RaiseModified(new ModifyEventArgs(new PropertiesRedoUndoCommand(col)));
            }
        }
        protected void RaiseModified(ModifyEventArgs e)
        {
            if (Modified != null)
            {
                Modified(this, e);
            }
        }


        private bool m_isUndoRedoing;
        public void BeginUndoRedo()
        {
            m_isUndoRedoing = true;
        }
        public void EndUndoRedo()
        {
            m_isUndoRedoing = false;
        }

        public void CancelEdit()
        {
            lock (m_EditCacheDic)
            {
                var col = m_EditCacheDic.Values.ToList();
                m_EditCacheDic.Clear();
                var tmp = new PropertiesRedoUndoCommand(col);
                tmp.Undo();
            }
        }

        public event PropertyChangedEventHandler PropertyChanged;
        public delegate void ModifiedEventHandler(object sender, ModifyEventArgs e);
        public event ModifiedEventHandler Modified;

        public virtual void WirteXml(System.Xml.XmlWriter xmwr)
        {
            if (xmwr == null)
            {
                return;
            }

            xmwr.WriteStartElement(ElementName);
            OnWritingAttributes(xmwr);
            OnWritingElements(xmwr);
            xmwr.WriteEndElement();
        }

        protected virtual void OnWritingAttributes(System.Xml.XmlWriter xmwr)
        {

        }
        protected virtual void OnWritingElements(System.Xml.XmlWriter xmwr)
        {

        }
    }
    public class ModifyEventArgs : EventArgs
    {
        public PropertiesRedoUndoCommand Command { get; set; }
        public ModifyEventArgs(PropertiesRedoUndoCommand Command)
        {
            this.Command = Command;
        }
    }
}
