package org.sedi.emp.restextractor.persistence

import org.sedi.emp.restextractor.model.masterdata.Well
import org.springframework.data.repository.CrudRepository
import java.util.*

interface WellRepository : CrudRepository<Well, UUID> {

    override fun findById(id: UUID): Optional<Well>
}
