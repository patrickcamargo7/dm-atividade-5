package ifpr.dispositivosmoveis.atividade5

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_choose_recipient.*

class ChooseRecipientFragment : Fragment(), OnClickListener {
    var navController: NavController? = null
    private var type: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            type = it.getString(ARG_TYPE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_choose_recipient, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        view.findViewById<Button>(R.id.next_btn).setOnClickListener(this)
        view.findViewById<Button>(R.id.cancel_btn).setOnClickListener(this)

        var labelType = view.findViewById<TextInputLayout>(R.id.labelType)

        if (type.equals(TYPE_RECEIVE)) {
            labelType.helperText = resources.getText(R.string.payer)
        } else {
            labelType.helperText = resources.getText(R.string.recipient)
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.next_btn -> {
                if (!TextUtils.isEmpty(input_recipient.text.toString())) {
                    val bundle = bundleOf("recipient" to input_recipient.text.toString(), "type" to type)
                    navController!!.navigate(R.id.action_chooseRecipientFragment_to_specifyAmountFragment, bundle)
                } else {
                    Toast.makeText(activity, resources.getText(R.string.choose_person), Toast.LENGTH_SHORT).show()
                }
            }
            R.id.cancel_btn -> requireActivity()?.onBackPressed()
        }
    }

    companion object{
        const val ARG_TYPE = "type"
        const val TYPE_RECEIVE = "receive";
        const val TYPE_SEND = "send";
    }
}