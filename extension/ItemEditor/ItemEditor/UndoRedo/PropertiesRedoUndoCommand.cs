using System.Collections.Generic;
using System.Threading.Tasks;

namespace ItemEditor
{
    public class PropertiesRedoUndoCommand
        : RedoUndoCommandBase
    {
        internal List<PropertyRedoUndoCommand> m_list = new List<PropertyRedoUndoCommand>();
        public PropertiesRedoUndoCommand(ICollection<PropertyRedoUndoCommand> cols)
        {
            if (cols != null)
            {
                m_list.AddRange(cols);
            }
        }

        public override bool CanInvoke
        {
            get
            {
                return true;
            }
        }

        public override void RedoInvoke()
        {
            foreach (var item in m_list)
            {
                item.RedoInvoke();
            }
        }

        public override void UndoInvoke()
        {
            foreach (var item in m_list)
            {
                item.UndoInvoke();
            }
        }
    }
}
