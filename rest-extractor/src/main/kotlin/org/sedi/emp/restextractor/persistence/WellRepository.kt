package org.sedi.emp.restextractor.persistence

import org.sedi.emp.restextractor.model.masterdata.Well
import org.springframework.data.repository.CrudRepository

interface WellRepository : CrudRepository<Well, Long> {}
