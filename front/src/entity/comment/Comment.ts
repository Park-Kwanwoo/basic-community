import { Transform } from 'class-transformer'
import { DateTimeFormatter, LocalDateTime } from '@js-joda/core'

export default class Comment {
  public id = 0
  public author = ''
  public password = ''
  public content = ''

  @Transform(({ value }) => LocalDateTime.parse(value, DateTimeFormatter.ISO_LOCAL_DATE_TIME))
  public createdDate = LocalDateTime.now()

  public getDisplayCreatedDate() {
    return this.createdDate.format(DateTimeFormatter.ofPattern('yyyy-MM-dd HH:mm'))
  }

  public getDisplaySimpleCreatedDate() {
    return this.createdDate.format(DateTimeFormatter.ofPattern('yyyy-MM-dd'))
  }
}
