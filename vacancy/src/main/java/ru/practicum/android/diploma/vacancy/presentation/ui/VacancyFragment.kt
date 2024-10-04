package ru.practicum.android.diploma.vacancy.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.practicum.android.diploma.commonutils.Utils
import ru.practicum.android.diploma.data.db.dao.VacancyDao_Impl
import ru.practicum.android.diploma.ui.R
import ru.practicum.android.diploma.vacancy.databinding.FragmentVacancyBinding
import ru.practicum.android.diploma.vacancy.presentation.ui.state.VacancyInputState
import ru.practicum.android.diploma.vacancy.presentation.viewmodel.VacancyDetailViewModel
import ru.practicum.android.diploma.vacancy.presentation.viewmodel.state.VacancyDetailState

class VacancyFragment : Fragment() {

    private var _binding: FragmentVacancyBinding? = null
    private val binding get() = _binding!!

    private val vacancyDetailViewModel: VacancyDetailViewModel by viewModel()
    private var idDb: Int = 0
    private var idNetwork: String = ""
    private var argsState: Int = 2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVacancyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val alternateUrl = ""
        binding.vacancyHeader.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.shareButton.setOnClickListener {
            Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, alternateUrl)
                type = "text/plain"
                Intent.createChooser(this, null)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context?.startActivity(this)
            }
        }

        argsState = requireArguments().getInt(ARGS_STATE)
        idDb = requireArguments().getInt(VACANCY_ID_DB)
        idNetwork = requireArguments().getString(VACANCY_ID_NETWORK).toString()

        if (argsState == INPUT_NETWORK_STATE) {
            vacancyDetailViewModel.showVacancyNetwork(idNetwork)
        }
        if (argsState == INPUT_DB_STATE) {
            vacancyDetailViewModel.showVacancyDb((idDb))
        }

        vacancyDetailViewModel.observeVacancyState().observe(viewLifecycleOwner) { state ->
            when (state) {
                is VacancyDetailState.Loading -> {
                    showLoading()
                }

                is VacancyDetailState.Content -> {
                    showContent(
                        state.vacancy.nameVacancy,
                        state.vacancy.salary,
                        state.vacancy.urlLogo,
                        state.vacancy.nameCompany,
                        state.vacancy.location,
                        state.vacancy.experience,
                        state.vacancy.employment,
                        state.vacancy.description,
                        "key skills"
                    )
                }

                is VacancyDetailState.Error -> {
                    showError()
                }

                is VacancyDetailState.Empty -> {
                    showEmpty()
                }
            }

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading() {
        binding.vacancyNotFoundError.visibility = View.GONE
        binding.vacancyServerError.visibility = View.GONE
        binding.progressbar.visibility = View.VISIBLE
    }

    private fun showContent(name: String, salary: String, urlLogo: String?,
                            company: String, city: String, experience: String,
                            conditions: String, description: String, keys: String) {
        binding.progressbar.visibility = View.GONE
        binding.vacancyServerError.visibility = View.GONE
        binding.progressbar.visibility = View.GONE
        binding.vacancyName.text = name
        binding.vacancySalary.text = salary
        if (urlLogo != null) {
            Glide.with(this)
                .load(urlLogo)
                .placeholder(R.drawable.placeholder_logo_item_favorite)
                .transform(
                    CenterCrop(),
                    context?.let {
                        Utils.doToPx(
                            RADIUS_ROUND_VIEW,
                            it
                        )
                    }?.let {
                        RoundedCorners(
                            it
                        )
                    }
                )
                .transform()
                .into(binding.vacancyImage)
        }
        binding.vacancyCompany.text = company
        binding.vacancyCity.text = city
        binding.vacancyExperienceInfo.text = experience
        binding.vacancyConditions.text = conditions
        binding.vacancyDescriptionInfo.text = description
        binding.vacancyKeySkillsInfo.text = keys
    }

    private fun showError() {
        binding.progressbar.visibility = View.GONE
        binding.vacancyNotFoundError.visibility = View.GONE
        binding.vacancyServerError.visibility = View.VISIBLE
    }

    private fun showEmpty() {
        binding.progressbar.visibility = View.GONE
        binding.vacancyServerError.visibility = View.GONE
        binding.vacancyNotFoundError.visibility = View.VISIBLE
    }

    companion object {
        private const val VACANCY_ID_NETWORK = "vacancy_instance"
        private const val VACANCY_ID_DB = "vacancy_id"

        private const val ARGS_STATE = "args_state"

        private const val INPUT_NETWORK_STATE = 0
        private const val INPUT_DB_STATE = 1

        private const val RADIUS_ROUND_VIEW = 12f

        fun createArgs(vacancyInputState: VacancyInputState): Bundle {
            return when (vacancyInputState) {
                is VacancyInputState.VacancyNetwork -> {
                    bundleOf(
                        ARGS_STATE to INPUT_NETWORK_STATE,
                        VACANCY_ID_NETWORK to vacancyInputState.id
                    )
                }

                is VacancyInputState.VacancyDb -> {
                    bundleOf(
                        ARGS_STATE to INPUT_DB_STATE,
                        VACANCY_ID_DB to vacancyInputState.id
                    )
                }
            }
        }
    }
}
