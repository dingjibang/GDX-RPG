using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Threading;

namespace ItemEditor
{
	public class RedoUndoClass
	{
		Stack<RedoUndoCommandBase> m_RedoStack = new Stack<RedoUndoCommandBase>();
		Stack<RedoUndoCommandBase> m_UndoStack = new Stack<RedoUndoCommandBase>();

        public bool CanRedo { 
         get{   lock (m_UndoStack)
        {
            return m_RedoStack.Count != 0; 
        }}
        }

        public bool CanUndo
        {
            get
            {
                lock (m_UndoStack)
                {
                    return m_UndoStack.Count != 0;
                }
            }
        }

        private Task m_CurTask = null;

        public void Clear()
        {
            m_RedoStack.Clear();
            m_UndoStack.Clear();
        }

		public async void Undo()
		{
            if (m_CurTask!= null)
            {
                await m_CurTask;
            }
		    m_CurTask =	Task.Run(() =>
			{
				lock (m_UndoStack)
				{
					if (m_UndoStack.Count == 0)
					{
						return;
					}
					RedoUndoCommandBase cmm = m_UndoStack.Pop();
                    if (cmm.CanInvoke)
                    {
                        cmm.UndoInvoke();
                    }
                    else cmm.Undo();
					m_RedoStack.Push(cmm);
				}
			});
		}

		public async void Redo()
		{
            if (m_CurTask != null)
            {
                await m_CurTask;
            }
            m_CurTask = Task.Run(() =>
{
	lock (m_UndoStack)
	{
		if (m_RedoStack.Count == 0)
		{
			return;
		}
		RedoUndoCommandBase cmm = m_RedoStack.Pop();
        if (cmm.CanInvoke)
        {
            cmm.RedoInvoke();
        }
        else cmm.Redo();
		m_UndoStack.Push(cmm);
	}
});
		}

		public async void PushCommand(RedoUndoCommandBase cmm)
		{
            if (m_CurTask != null)
            {
                await m_CurTask;
            }
            m_CurTask = Task.Run(() =>
			{
				lock (m_UndoStack)
				{
					m_UndoStack.Push(cmm);
					m_RedoStack.Clear();
				}
			});
		}
	}
    public class CutRedoUndoCommand : RedoUndoCommandBase
	{
        public ViewModelBase OldElement { get; set; }
        public ViewModelBase NewElement { get; set; }


		public override async void Redo()
		{
            //TODO:
		}

		public override async void Undo()
		{
            //TODO:
		}
	}
	public class ItemADDRedoUndoCommand : RedoUndoCommandBase
	{
        private ItemBaseViewModel m_Target;
        public ItemBaseViewModel Target
        {
            get { return m_Target; }
            set { m_Target = value; }
        }
		public override async void Redo()
		{
            //TODO:
			//base.Redo();
		}

		public override async void Undo()
		{
            //TODO:
			// base.Undo();
		}
	}

	public class ItemDeleteRedoUndoCommand : RedoUndoCommandBase
	{
		private ItemBaseViewModel m_Target;
		public ItemBaseViewModel Target
		{
			get { return m_Target; }
			set { m_Target = value; }
		}

		public override async void Redo()
		{
            //TODO:
			
		}

		public override async void Undo()
		{
            //TODO:
			
		}
	}

	public class ActionPropertyChangedRedoUndoCommand : PropertyRedoUndoCommand
	{
		private ViewModelBase m_TargetElement;
		public ViewModelBase TargetElement
		{
			get { return m_TargetElement; }
			set { m_TargetElement = value; }
		}
		public ActionPropertyChangedRedoUndoCommand(object Target, string PropertyName, object OldValue, object NewValue)
			: base(Target, PropertyName, OldValue, NewValue)
		{
		}
	}

	public class ActionPropertiesChangedRedoUndoCommand : PropertiesRedoUndoCommand
	{
		private ViewModelBase m_TargetElement;
		public ViewModelBase TargetElement
		{
			get { return m_TargetElement; }
			set { m_TargetElement = value; }
		}

		public ActionPropertiesChangedRedoUndoCommand(ICollection<PropertyRedoUndoCommand> cols)
			: base(cols)
		{
		}
	}
}
