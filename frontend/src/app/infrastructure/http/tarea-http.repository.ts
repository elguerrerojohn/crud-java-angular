import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { EstadoTarea } from '@core/domain/models/estado-tarea.enum';
import { Tarea, TareaInput } from '@core/domain/models/tarea.model';
import { TareaRepository } from '@core/domain/repositories/tarea.repository';
import { environment } from '@env/environment';
import { TareaResponseDto } from './dto/tarea.dto';
import { TareaMapper } from './mappers/tarea.mapper';

/**
 * Adaptador de salida: implementa {@link TareaRepository} con HttpClient.
 */
@Injectable()
export class TareaHttpRepository extends TareaRepository {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = `${environment.apiUrl}/tareas`;

  override listar(estado: EstadoTarea | null): Observable<Tarea[]> {
    const options = estado ? { params: new HttpParams().set('estado', estado) } : {};
    return this.http
      .get<TareaResponseDto[]>(this.baseUrl, options)
      .pipe(map((dtos) => dtos.map(TareaMapper.toDomain)));
  }

  override obtenerPorId(id: number): Observable<Tarea> {
    return this.http
      .get<TareaResponseDto>(`${this.baseUrl}/${id}`)
      .pipe(map(TareaMapper.toDomain));
  }

  override crear(input: TareaInput): Observable<Tarea> {
    return this.http
      .post<TareaResponseDto>(this.baseUrl, TareaMapper.toRequest(input))
      .pipe(map(TareaMapper.toDomain));
  }

  override actualizar(id: number, input: TareaInput): Observable<Tarea> {
    return this.http
      .put<TareaResponseDto>(`${this.baseUrl}/${id}`, TareaMapper.toRequest(input))
      .pipe(map(TareaMapper.toDomain));
  }

  override eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
