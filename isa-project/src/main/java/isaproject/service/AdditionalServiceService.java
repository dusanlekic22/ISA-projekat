package isaproject.service;

import java.util.Set;

import isaproject.dto.AdditionalServiceDTO;

public interface AdditionalServiceService {
	AdditionalServiceDTO findById(Long id);
	AdditionalServiceDTO save(AdditionalServiceDTO additionalService);
	AdditionalServiceDTO deleteById(Long id);
	Set<AdditionalServiceDTO> findFree();
	Set<AdditionalServiceDTO> findByCottageId(Long id);
	Set<AdditionalServiceDTO> findByCottageReservationId(Long id);
	Set<AdditionalServiceDTO> findByCottageQuickReservationId(Long id);
	Set<AdditionalServiceDTO> findByBoatReservationId(Long id);
	Set<AdditionalServiceDTO> findByBoatQuickReservationId(Long id);
	Set<AdditionalServiceDTO> findByBoatId(Long id);
	Set<AdditionalServiceDTO> findByFishingCourseId(Long id);
	Set<AdditionalServiceDTO> findByFishingReservationId(Long id);
	Set<AdditionalServiceDTO> findByFishingQuickReservationId(Long id);
}
