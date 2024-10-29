// EraseImageDialogFragment.java
// Allows user to erase image
package com.deitel.addressbook;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;

//class for the Select Color dialog
public class DeleteFragment extends DialogFragment
{
    private DetailsFragment.DetailsFragmentListener listener;
    private long rowID;

    public DetailsFragment.DetailsFragmentListener getListener() {
        return listener;
    }


    public void setListener(DetailsFragment.DetailsFragmentListener listener) {
        this.listener = listener;
    }

    public long getRowID() {
        return rowID;
    }

    public void setRowID(long rowID) {
        this.rowID = rowID;
    }

    // create an AlertDialog and return it
    public Dialog onCreateDialog(Bundle bundle)
    {
        // create a new AlertDialog Builder
        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.confirm_title);
        builder.setMessage(R.string.confirm_message);

        // provide an OK button that simply dismisses the dialog
        builder.setPositiveButton(R.string.button_delete,
                new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(
                            DialogInterface dialog, int button)
                    {
                        final DatabaseConnector databaseConnector =
                                new DatabaseConnector(getActivity());

                        // AsyncTask deletes contact and notifies listener
                        AsyncTask<Long, Object, Object> deleteTask =
                                new AsyncTask<Long, Object, Object>()
                                {
                                    @Override
                                    protected Object doInBackground(Long... params)
                                    {
                                        databaseConnector.deleteContact(params[0]);
                                        return null;
                                    }

                                    @Override
                                    protected void onPostExecute(Object result)
                                    {
                                        getListener().onContactDeleted();
                                    }
                                }; // end new AsyncTask

                        // execute the AsyncTask to delete contact at rowID
                        deleteTask.execute(new Long[] { getRowID() });
                    } // end method onClick
                } // end anonymous inner class
        ); // end call to method setPositiveButton

        builder.setNegativeButton(R.string.button_cancel, null);
        return builder.create(); // return the AlertDialog
    } // end method onCreateDialog

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getArguments() != null) {
            setRowID(getArguments().getLong("rowID"));
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        setListener(null); // Clean up listener to avoid memory leaks
    }


} // end class EraseImageDialogFragment

/**************************************************************************
 * (C) Copyright 1992-2012 by Deitel & Associates, Inc. and               *
 * Pearson Education, Inc. All Rights Reserved.                           *
 *                                                                        *
 * DISCLAIMER: The authors and publisher of this book have used their     *
 * best efforts in preparing the book. These efforts include the          *
 * development, research, and testing of the theories and programs        *
 * to determine their effectiveness. The authors and publisher make       *
 * no warranty of any kind, expressed or implied, with regard to these    *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or       *
 * consequential damages in connection with, or arising out of, the       *
 * furnishing, performance, or use of these programs.                     *
 **************************************************************************/
