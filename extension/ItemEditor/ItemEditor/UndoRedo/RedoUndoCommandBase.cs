using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ItemEditor
{
   public abstract class RedoUndoCommandBase
    {
       public virtual bool CanInvoke
       {
           get { return false; }
       }

       public virtual void RedoInvoke()
        {
        }

       public virtual void UndoInvoke()
        {
        }

       public virtual async void Redo()
       {
           await Task.Run(new Action(() =>
           {
               RedoInvoke();
           }));
       }
       public virtual async void Undo()
       {
           await Task.Run(new Action(() =>
           {
               UndoInvoke();
           }));
       }

    }
}
