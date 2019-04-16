package th.co.knightfrank.data_java.data.mapper

import th.co.knightfrank.data_java.data.parcels.ProjectInfoParcel
import th.co.knightfrank.data_java.data.responses.projects.ProjectInfoResponse

class ProjectInfoResponseMapper : Mapper<ProjectInfoResponse?, ProjectInfoParcel?> {
    override fun transform(source: ProjectInfoResponse?): ProjectInfoParcel = ProjectInfoParcel(
            _activeStatus = source?._activeStatus,
            _projectAddress = source?._projectAddress,
            _projectID = source?._projectID,
            _projectNameTh = source?._projectNameTh,
            _projectNameEn = source?._projectNameEn,
            _projectCode = source?._projectCode
    )
}