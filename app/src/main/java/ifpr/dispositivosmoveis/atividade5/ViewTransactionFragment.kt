package ifpr.dispositivosmoveis.atividade5

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ifpr.dispositivosmoveis.atividade5.adapters.RecordAdapter
import ifpr.dispositivosmoveis.atividade5.util.UserSession

class ViewTransactionFragment : Fragment() {
    lateinit var adapter: RecordAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_view_transaction, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var authUserId: Long = UserSession.getUserAuthId(requireActivity())

        var listTransactions = view.findViewById<RecyclerView>(R.id.listTransactions)

        context?.let {
            adapter = RecordAdapter(it, authUserId)
            listTransactions.adapter = adapter
            listTransactions.layoutManager = LinearLayoutManager(it, LinearLayoutManager.VERTICAL, false)
        }
    }
}