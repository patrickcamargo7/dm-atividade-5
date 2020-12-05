package ifpr.dispositivosmoveis.atividade5

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import ifpr.dispositivosmoveis.atividade5.dao.RecordDAO
import ifpr.dispositivosmoveis.atividade5.models.Record
import ifpr.dispositivosmoveis.atividade5.util.UserSession
import kotlinx.android.synthetic.main.fragment_specify_amount.*
import java.lang.Exception
import java.math.BigDecimal


private const val ARG_RECIPIENT = "recipient"

class SpecifyAmountFragment : Fragment(), View.OnClickListener {
    lateinit var navController: NavController
    private var recipient: String? = null
    private var type: String? = null

    private var dao: RecordDAO = RecordDAO()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            recipient = it.getString(ARG_RECIPIENT)
            type = it.getString(ChooseRecipientFragment.ARG_TYPE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_specify_amount, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        view.findViewById<Button>(R.id.send_btn).setOnClickListener(this)
        view.findViewById<Button>(R.id.cancel_btn).setOnClickListener(this)

        val message = "Sending money to $recipient"
        view.findViewById<TextView>(R.id.recipient).text = message

        var tvRecipient = view.findViewById<TextView>(R.id.recipient)

        tvRecipient.text = resources.getText(R.string.sending_to).toString().format(recipient)

        if (type.equals(ChooseRecipientFragment.TYPE_RECEIVE)) {
            tvRecipient.text = resources.getText(R.string.receive_from).toString().format(recipient)
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.send_btn -> {
                if (!TextUtils.isEmpty(input_amount.text.toString())) {
                    val amount = Money(BigDecimal(input_amount.text.toString()))
                    val bundle = bundleOf("recipient" to recipient, "amount" to amount, "type" to type)

                    if (!createRecord()) {
                        Toast.makeText(activity, resources.getText(R.string.invalid_data), Toast.LENGTH_SHORT).show()
                        return
                    }

                    navController!!.navigate(R.id.action_specifyAmountFragment_to_confirmationFragment, bundle)
                } else {
                    Toast.makeText(activity, resources.getText(R.string.enter_amount), Toast.LENGTH_SHORT).show()
                }
            }
            R.id.cancel_btn -> requireActivity()?.onBackPressed()
        }
    }

    fun createRecord() : Boolean {
        var value = input_amount.text.toString().toFloat()

        if (recipient != null) {
            return try {
                val send: Boolean = type.equals(ChooseRecipientFragment.TYPE_SEND);
                var record: Record = Record(value = value, person = recipient!!, remarks = "", send = send)
                record.userId = UserSession.getUserAuthId(requireActivity())
                dao.insert(record) { recordAPI ->
                }
                true
            } catch (e: Exception) {
                false;
            }
        }

        return false;
    }
}