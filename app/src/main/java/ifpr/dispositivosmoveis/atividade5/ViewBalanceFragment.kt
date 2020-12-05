package ifpr.dispositivosmoveis.atividade5

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ifpr.dispositivosmoveis.atividade5.dao.RecordDAO
import ifpr.dispositivosmoveis.atividade5.util.UserSession

class ViewBalanceFragment : Fragment() {

    private var dao: RecordDAO = RecordDAO()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_view_balance, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var userId: Long = UserSession.getUserAuthId(requireActivity())
        var send: Double = 0.0
        var receive: Double = 0.0
        var total: Double = 0.0

        dao.filter(userId, true) { transactionsAPI ->
            run {
                transactionsAPI.forEach { t -> send += t.value }
                view.findViewById<TextView>(R.id.tvBalanceSend).text = """${send}$"""

                dao.filter(userId, false) { transactionsAPI ->
                    run {
                        transactionsAPI.forEach { t -> receive += t.value }
                        total = receive - send;
                        view.findViewById<TextView>(R.id.tvBalanceValue).text = "$total$"
                        view.findViewById<TextView>(R.id.tvBalanceReceive).text = "$receive$"
                    }
                }
            }
        }
    }
}