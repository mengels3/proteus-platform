package org.sedi.emp.restextractor.persistence

import org.sedi.emp.restextractor.model.masterdata.Well
import org.springframework.data.repository.CrudRepository
import java.util.*

interface WellRepository : CrudRepository<Well, Long> {

    override fun findById(id: Long): Optional<Well>
}
